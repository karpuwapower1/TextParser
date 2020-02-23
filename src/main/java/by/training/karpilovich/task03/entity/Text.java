package by.training.karpilovich.task03.entity;

public class Text {

	private String text;

	private Text() {
	}

	private Text(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return (text == null) ? 0 : text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Text other = (Text) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		}
		return text.equals(other.text);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [text=" + text + "]";
	}
}
