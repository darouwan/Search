import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.function.CustomScoreProvider;

public class MyCustomScoreProvider extends CustomScoreProvider {
    String[] filenames = null;

    public MyCustomScoreProvider(IndexReader reader) {
	super(reader);
	try {
	    filenames = FieldCache.DEFAULT.getStrings(reader, "filename");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public float customScore(int doc, float subQueryScore, float valSrcScore)
	    throws IOException {
	// 根据doc获取文件名
	String filename = filenames[doc];
	// 判断文件后缀是否为txt结尾,是把评分*10 否则/10
	if (filename.endsWith(".txt")) {
	    return subQueryScore * 10;
	} else {
	    return subQueryScore / 10;
	}
    }
}
