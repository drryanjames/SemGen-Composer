import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;


/*
 * Core of SemGen. Contains all information core to SemGen
 */
public class SemGen {
	// Model repository
	private ModelRepository _repository;
	
	public SemGen(){
		_repository = new ModelRepository();
	}
	
	/*
	 * Get model repository
	 */
	public ModelRepository getModelRepository(){
		return _repository;
	}
	
	/*
	 * Add model from file.
	 * If parsing errors are encountered this function will
	 * throw specific exceptions
	 */
	public Model addModelFromFile(File file) throws Exception {
		// Get the file name minus the ext
		String modelName = FilenameUtils.getBaseName(file.getName());
		
		// If we already have this model we
		// dont want to create it again
		if(_repository.hasModel(modelName))
			return null;
		
		// For now assume that this is a valid model
		// and just get the name
		// TODO: Validate model and create object from model contents
		Model newModel = CreateDummyModel(modelName);
		
		// Add to repository
		return _repository.addModel(newModel);
	}
	
	/*
	 * Creates dummy model with dummy properties
	 */
	private Model CreateDummyModel(String name){
		ArrayList<IModelProperty> modelProperties = new ArrayList<IModelProperty>();
		modelProperties.add(new ModelProperty("Property 1", "P1", "mx + 1"));
		modelProperties.add(new ModelProperty("Property 2", "P2", "mx + 2"));
		modelProperties.add(new ModelProperty("Property 3", "P3", "mx + 3"));
		modelProperties.add(new ModelProperty("Property 4", "P4", "mx + 4"));
		modelProperties.add(new ModelProperty("Property 5", "P5", "mx + 5"));
		
		return new Model(name, modelProperties);
	}
}