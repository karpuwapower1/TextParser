package by.training.karpilovich.task03.entity;

public enum ParserType {

	TEXT("(.|\\r|\\n|\\r\\n)+"), 
	PARAGRAPH(".+(\\r|\\n|\\r\\n){1}"), 
	PHRASE("[^\\.;!\\?]+[\\.;!\\?]+"),
	LEXEME("[\\s][^\\s]+|[^\\s]+[\\s]"), 
	POLISH_NOTATION("[[\\d]*[%&()*+-/<=>^\\|~&&[^,.]]+[\\d]*]{2,}"),
	WORD("([\\w]+[^\\w])"), 
	SYMBOL(".");
	
	private String regex;

	private ParserType(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

}
