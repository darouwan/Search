import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class Spatial_Indexer {

	public HashMap<String, Location> readInLocFile(String LocFile) {
		HashMap<String, Location> locHM = new HashMap<String, Location>();
		BufferedReader reader = null;
		File locFile = new File(LocFile);
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(locFile));
			String tempString = null;

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号

				String[] data = tempString.split(" ");
				locHM.put(data[0], new Location(Float.parseFloat(data[1]),
						Float.parseFloat(data[2])));
				System.out
						.println(data[0] + ": x=" + data[1] + " y=" + data[2]);

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return locHM;
	}

	/**
	 * @param dirPath
	 *            dirPath species the path of the directory that contains all
	 *            the text files to be indexed
	 * @param indexPath
	 *            the path of the directory that you need to put your index
	 *            files in
	 * @param LocFile
	 *            is the path of the file that contains all the location
	 *            information
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 */
	public void generateIndex(String dirPath, String indexPath, String LocFile)
			throws CorruptIndexException, LockObtainFailedException,
			IOException {
		// fileDir is the directory that contains the text files to be indexed
		File fileDir = new File(dirPath);
		// indexDir is the directory that hosts Lucene's index files
		File indexDir = new File(indexPath);

		HashMap<String, Location> locHm = this.readInLocFile(LocFile);

		Analyzer a = new StandardAnalyzer(Version.LUCENE_36);

		IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir),
				new IndexWriterConfig(Version.LUCENE_36, a));

		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();

		NumericField xField = new NumericField("x", Field.Store.YES, true);
		NumericField yField = new NumericField("y", Field.Store.YES, true);
		// Add documents to the index
		for (int i = 0; i < textFiles.length; i++) {
			if (textFiles[i].isFile()
					&& textFiles[i].getName().endsWith(".txt")) {
				System.out.println("File " + textFiles[i].getCanonicalPath()
						+ " is being indexed");
				Reader textReader = new FileReader(textFiles[i]);
				Document document = new Document();
				Location loc = locHm.get(textFiles[i].getName());

				xField.setFloatValue(loc.x);// .setDoubleValue(loc.x);

				yField.setFloatValue(loc.y);

				// document.add(new field("fileName",))
				document.add(new Field("fileName", textFiles[i].getName(),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				document.add(new Field("content", textReader,
						Field.TermVector.YES));
				document.add(new Field("path", textFiles[i].getPath()
						.getBytes()));
				document.add(xField);
				document.add(yField);
				writer.addDocument(document);
			}
		}

		// writer.optimize();
		// writer.addDocument(doc);

		writer.close();
		long endTime = new Date().getTime();

		System.out
				.println("It took "
						+ (endTime - startTime)
						+ " milliseconds to create an index for the files in the directory "
						+ fileDir.getPath());
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws LockObtainFailedException
	 * @throws CorruptIndexException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws CorruptIndexException,
			LockObtainFailedException, IOException, ParseException {
		// TODO Auto-generated method stub

		Spatial_Indexer si = new Spatial_Indexer();

		String dirPath = "SampleData2";
		String indexPath = "index";
		String LocFile = "sampleLoc2.txt";
		si.generateIndex(dirPath, indexPath, LocFile);

	}

}
