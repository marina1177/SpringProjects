
package hello;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

public class HelloLucene {
    public static void main(String[] args) throws IOException, ParseException
    {
        //Create a new index and open a writer
        Directory dir = new RAMDirectory();
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);

        //Create a document to index
        Document doc = new Document();
        doc.add(new TextField("text", "Hello World!", Field.Store.YES));

        //Index the document and close the writer
        System.out.println("Indexing document: " + doc);
        writer.addDocument(doc);
        writer.close();

        //Open an IndexSearcher
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //Create a query
        QueryParser parser = new QueryParser("text", analyzer);
        Query query = parser.parse("world");

        //Search for results of the query in the index
        System.out.println("Searching for: \"" + query + "\"");
        TopDocs results = searcher.search(query, 10);
        for (ScoreDoc result : results.scoreDocs) {
            Document resultDoc = searcher.doc(result.doc);
            System.out.println("score: " + result.score +
                    " -- text: " + resultDoc.get("text"));
        }
        reader.close();
    }
}
