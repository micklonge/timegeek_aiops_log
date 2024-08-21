package framework.ai.nn.weights;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import framework.ai.nn.NNObject;
import framework.ai.nn.NNObject.NNRelation;

public class NNWeightAdagrad implements NNWeightInterface {
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void flushWeight(NNObject nnObject) throws Exception {
		int i;
		
		NNRelation nnRelation = null;
		
		for (i = 0; i < nnObject.nnRelationSize(); ++i) {
			nnRelation = nnObject.getNNRelation(i);
			if (nnRelation == null) {
				logger.info("the nnObject is bad, the nnRelation is null");
				return;
			}
			
			flushWeight(nnObject, nnRelation);
		}
	}
	
	private void flushWeight(NNObject nnObject, NNRelation nnRelation) throws Exception {
		int i, j;
		double nt = 0.0;
		double gt = 0.0;
		
		for (i = 0; i < nnRelation.getWeightListList().size(); ++i) {
			for (j = 0; j < nnRelation.getWeightListList().get(0).size(); ++j) {
				nt = nnRelation.getPreOffWeightListList().get(i).get(j);
				nt = nt + nnRelation.getOffWeightListList().get(i).get(j) * nnRelation.getOffWeightListList().get(i).get(j);
				
				gt = nnObject.getLearningRate() / Math.sqrt(nt + 0.0000001) * nnRelation.getOffWeightListList().get(i).get(j);
				
				nnRelation.getWeightListList().get(i).set(j, nnRelation.getWeightListList().get(i).get(j) - gt);
				nnRelation.getPreOffWeightListList().get(i).set(j, nt);
			}
		}
	}

}
