import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.ValueSourceQuery;

public class MyCustomScoreQuery extends CustomScoreQuery {
	public MyCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery) {
		super(subQuery, valSrcQuery);
	}

	@Override
	protected CustomScoreProvider getCustomScoreProvider(IndexReader reader)
			throws IOException {
		// 默认情况评分是根据原有的评分*传入进来的评分
		// return super.getCustomScoreProvider(reader);

		return new MyCustomScoreProvider(reader);
	}

}
