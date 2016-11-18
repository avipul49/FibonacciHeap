
public class HashTag implements Comparable<HashTag> {
	String tag;
	int count;

	@Override
	public int compareTo(HashTag o) {
		return o.count - count;
	}

	@Override
	public int hashCode() {
		return tag.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return tag.equals(((HashTag) obj).tag);
	}
}
