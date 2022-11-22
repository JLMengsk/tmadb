package ca.ubc.gpec.tmadb;

public class ScorerInfo {

	// instance variables ...
	Tma_results tma_result
	def scoreType
	
	/**
	 * default constructor
	 * @param tma_scorer1
	 * @param tma_scorer2
	 * @param tma_scorer3
	 * @param scoring_date
	 * @param scoreType
	 */
	public ScorerInfo(Tma_results tma_result) {
		this.tma_result = tma_result
		this.scoreType = ScoreType.getScoreType(tma_result)
	}
	
	Tma_scorers getTma_scorer1(){return tma_result.tma_scorer1}
	Tma_scorers getTma_scorer2(){return tma_result.tma_scorer2}
	Tma_scorers getTma_scorer3(){return tma_result.tma_scorer3}
	Date getScoring_date(){return tma_result.scoring_date}
	Date getReceived_date(){return tma_result.received_date}
	Tma_result_names getTma_result_name(){return tma_result.tma_result_name}
	def getScoreType(){return scoreType}
	
	@Override
	public int hashCode() {
		return 1;
	}
		
	@Override	
	public boolean equals(Object o) {
		boolean result = false
		if (o instanceof ScorerInfo) {
			ScorerInfo input = (ScorerInfo)o
			if (input.getTma_result_name() == this.getTma_result_name() &
                            input.getTma_scorer1() == this.getTma_scorer1() & 
                            input.getTma_scorer2() == this.getTma_scorer2() &
                            input.getTma_scorer3() == this.getTma_scorer3()
			) {
				// need to check scoreType
				if (this.getScoreType() instanceof String) {
					if (input.getScoreType() instanceof String) {
						if ( ((String)(this.getScoreType())).equals((String)(input.getScoreType())) ) {
							result = true
						}
					} else {
						result = false
					}
				} else {
					// scoreType must be instance of Ihc_score_category_groups
					if(input.getScoreType() instanceof Ihc_score_category_groups) {
						if(((Ihc_score_category_groups)(this.getScoreType())).id == ((Ihc_score_category_groups)(input.getScoreType())).id) {
							result = true // if id match from database, than its a match!!!
						}
					} else {
						result = false
					}
				}
			}
		}
		return result
	}
	
}
