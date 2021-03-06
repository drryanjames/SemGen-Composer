package semGen.models;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import semGen.models.Model;
import semGen.models.ModelRepository;


public class ModelRepositoryTest {
	private Model _model;
	private TestModelRepositoryActionListener _listener;
	private ModelRepository _repository;
	
	@Before
	public void setUp() throws Exception {
		_model = new Model("test model");
		_listener = new TestModelRepositoryActionListener();
		_repository = new ModelRepository();
	}

	@Test
	public void hasModel_NullModel_VerifyFalse() {
		assertFalse("Verify null is not in the repository",
				_repository.hasModel(null));
	}
	
	@Test
	public void hasModel_ModelInRepository_VerifyTrue() {
		assertFalse("Verify model not found",
				_repository.hasModel("model not in repository"));
	}
	
	@Test
	public void hasModel_ModelNotInRepository_VerifyTrue() {
		// Add model
		_repository.addModel(_model);
		
		assertTrue("Verify model found",
				_repository.hasModel(_model.getName()));
	}
	
	@Test
	public void getModels_NoModels_VerifyEmptyList() {
		assertEquals("Verify the model repository has 0 models",
				0,
				_repository.getModels().size());
	}
	
	@Test
	public void getModels_ModelInRepository_VerifyListContainsModel() {
		// Add model
		_repository.addModel(_model);
		
		assertEquals("Verify the model repository has 1 model",
				1,
				_repository.getModels().size());
		
		assertEquals("Verify model in repository",
				_model,
				_repository.getModels().get(0));
	}
	
	@Test
	public void getModels_ModifyList_VerifyListInRepositoryNotChanged() {
		// Add model
		_repository.addModel(_model);
		
		// Remove the model from the list
		ArrayList<Model> models = _repository.getModels();
		models.clear();
		
		assertEquals("Verify all models were removed from the list we retrieved",
				0,
				models.size());
		
		assertEquals("Verify the model repository list was unchanged",
				1,
				_repository.getModels().size());
	}
	
	@Test
	public void addModel_NullModel_VerifyModelNotAdded() {
		assertNull("Verify null not added to model repository",
				_repository.addModel(null));
	}
	
	@Test
	public void addModel_ValidModel_VerifyModelAdded() {
		assertSame("Verify valid model successfully added to repository",
				_model,
				_repository.addModel(_model));
	}
	
	@Test
	public void addModel_ModelAlreadyInRepository_VerifyModelNotAdded() {
		// Add model
		_repository.addModel(_model);
		
		assertNull("Verify models are not added if they already exist in the repository",
				_repository.addModel(_model));
	}
	
	@Test
	public void addModel_ValidModel_VerifyAddedModelListenerCalledWhenModelAdded() {
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Add Model
		_repository.addModel(_model);
		
		assertSame("Verify the added model listener was called with the correct model",
				_model,
				_listener.LastAddedModel);
	}
	
	@Test
	public void addModel_ValidModel_VerifyRemovedModelListenerNotCalledWhenModelAdded() {
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Add Model
		_repository.addModel(_model);
		
		assertNull("Verify the removed model listener was not called",
				_listener.LastRemovedModel);
	}
	
	@Test
	public void removeModel_Null_VerifyModelNotAdded() {
		assertFalse("Verify null models cannot be removed",
				_repository.removeModel(null));
	}
	
	@Test
	public void removeModel_ExistingModel_VerifyModelRemoved() {
		// Add model
		_repository.addModel(_model);
		
		assertTrue("Verify existing models can be removed",
				_repository.removeModel(_model));
	}
	
	@Test
	public void removeModel_ModelNotInRepository_VerifyModelNotRemoved() {
		assertFalse("Verify models that are not in the repository are not removed",
				_repository.removeModel(_model));
	}
	
	@Test
	public void removeModel_ExistingModel_VerifyRemovedModelListenerCalledWhenModelRemoved() {
		// Add model
		_repository.addModel(_model);
		
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Remove model
		_repository.removeModel(_model);

		assertSame("Verify listener called with correct model when model removed",
				_model,
				_listener.LastRemovedModel);
	}
	
	@Test
	public void removeModel_ExistingModel_VerifyAddedModelListenerNotCalledWhenModelRemoved() {
		// Add model
		_repository.addModel(_model);
		
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Remove model
		_repository.removeModel(_model);

		assertNull("Verify listener called with correct model when model removed",
				_listener.LastAddedModel);
	}
	
	@Test
	public void getModel_NullName_VerifyNullModelReturned(){
		assertNull("Verify null is returned when getModel is called with null",
				_repository.getModel(null));
	}
	
	@Test
	public void getModel_ModelDoesntExist_VerifyNullModelReturned(){
		assertNull("Verify null is returned when getModel is called with a non-existent model name",
				_repository.getModel("some model that doesnt exist"));
	}
	
	@Test
	public void getModel_ModelExists_VerifyModelReturned(){
		// Add model
		_repository.addModel(_model);
		
		assertSame("Veirfy getModel retrieved the model correctly",
				_model,
				_repository.getModel(_model.getName()));
	}
	
	@Test
	public void addModelRepositoryActionListener_Null_VerifyFalse() {
		assertFalse("Verify null listeners are not added to repository",
				_repository.addModelRepositoryActionListener(null));
	}
	
	@Test
	public void addModelRepositoryActionListener_ValidListener_VerifyTrue() {
		assertTrue("Verify valid listeners are added to repository",
				_repository.addModelRepositoryActionListener(_listener));
	}
	
	@Test
	public void removeModelRepositoryActionListener_Null_VerifyFalse() {
		assertFalse("Verify null listeners are not added to repository",
				_repository.removeModelRepositoryActionListener(null));
	}
	
	@Test
	public void removeModelRepositoryActionListener_ExistingListener_VerifyTrue() {
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		assertTrue("Verify existing listener removed from repository",
				_repository.removeModelRepositoryActionListener(_listener));
	}
}
