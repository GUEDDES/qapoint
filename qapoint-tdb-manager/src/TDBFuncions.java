import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;

/**
 * 
 */

/**
 * @author tosh
 *
 */
public class TDBFuncions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  // Assembler way: Make a TDB-back Jena model in the named directory.
		  // This way, you can change the model being used without changing the code.
		  // The assembler file is a configuration file.
		  // The same assembler description will work in Joseki. 
		  String assemblerFile = "Store/tdb-assembler.ttl" ;
		  Model model = TDBFactory.assembleModel(assemblerFile) ;
		  
//		  model
		  
		  model.close() ;
		  
		  
//		  String assemblerFile = "Store/tdb-assembler.ttl" ;
//		  Dataset dataset = TDBFactory.assembleDataset(assemblerFile) ;
//		  ...
//		  dataset.close() ;
	}

}
