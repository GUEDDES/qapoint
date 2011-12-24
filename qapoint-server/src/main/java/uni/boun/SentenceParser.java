package uni.boun;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.io.StringReader;

import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;  
import edu.stanford.nlp.ling.HasWord;  
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class SentenceParser {

	public static LexicalizedParser lp = null;
	
	static{
		lp = new LexicalizedParser("grammar/englishPCFG.ser.gz");
	}

  public static void main(String[] args) {
    
    if (args.length > 0) {
      demoDP(lp, args[0]);
    } else {
      sendQuestion("What is the best italian restaurant in Istanbul?");
    }
  }

  public static void setOptions(LexicalizedParser lp) {
    lp.setOptionFlags("-maxLength", "80", "-retainTmpSubcategories");        
  }

  public static void demoDP(LexicalizedParser lp, String filename) {
    // This option shows loading and sentence-segment and tokenizing
    // a file using DocumentPreprocessor
    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    // You could also create a tokenier here (as below) and pass it
    // to DocumentPreprocessor
    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
      Tree parse = lp.apply(sentence);
      parse.pennPrint();
      System.out.println();
      
      GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
      Collection tdl = gs.typedDependenciesCCprocessed(true);
      System.out.println(tdl);
      System.out.println();
    }
  }

  public static void sendQuestion(String sent2) {
    // This option shows parsing a list of correctly tokenized words
//    String[] sent = { "This", "is", "an", "easy", "sentence", "." };
//    List<CoreLabel> rawWords = new ArrayList<CoreLabel>();
//    for (String word : sent) {
//      CoreLabel l = new CoreLabel();
//      l.setWord(word);
//      rawWords.add(l);
//    }
//    Tree parse = lp.apply(rawWords);
//    parse.pennPrint();
//    System.out.println();

    // This option shows loading and using an explicit tokenizer
    TokenizerFactory<CoreLabel> tokenizerFactory = 
      PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
    List<CoreLabel> rawWords2 = 
      tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
    Tree parse = lp.apply(rawWords2);

    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
    System.out.println(tdl);

    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
    tp.printTree(parse);
    
    String subject = null;
    Vector subjectModifiers = new Vector();
    Vector subjectPrep = new Vector();
    Hashtable possibleMatches = new Hashtable();
    
    for (Iterator iterator = tdl.iterator(); iterator.hasNext();) {
		TypedDependency typedDependency = (TypedDependency) iterator.next();
		System.out.println(typedDependency.reln().getShortName());
		
		if(typedDependency.reln().getShortName().indexOf("subj")!=-1){
			System.out.println("subj");
			subject = typedDependency.dep().label().value();
			System.out.println(subject);
		
			Hashtable ontologyRefsHash = QueryRDF.getSemanticInfo(typedDependency.dep().label().value());
		}
		
		else if(typedDependency.reln().getShortName().indexOf("amod")!=-1){
			System.out.println("amod");
			System.out.println(typedDependency.dep().label().value());
		
			Hashtable ontologyRefsHash = QueryRDF.getSemanticInfo(typedDependency.dep().label().value());
		}
		else if(typedDependency.reln().getShortName().indexOf("prep")!=-1){
			System.out.println("prep");
			System.out.println(typedDependency.dep().label().value());
		
			Hashtable ontologyRefsHash = QueryRDF.getSemanticInfo(typedDependency.dep().label().value());
		}		
		
	}
    
    System.out.println();

//    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//    tp.printTree(parse);
  }

  private SentenceParser() {} // static methods only

}
