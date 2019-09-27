package com.previred.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

public class Buscar {
	
	   public static Analyzer analyzer = new StandardAnalyzer();
	    public static IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    public static RAMDirectory ramDirectory = new RAMDirectory();
	    public static IndexWriter indexWriter;
	
    private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception{
        //QueryParser qp = new QueryParser("apellido", new StandardAnalyzer());
        QueryParser qp = new QueryParser("apellido", new SpanishAnalyzer());
        Query firstNameQuery = qp.parse(firstName);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

 
    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get("C:\\Users\\clillo\\Downloads\\lucene-8.2.0\\demo\\index"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
    

    public static void searchIndexAndDisplayResults(Query query) {
        try {
        	Directory dir = FSDirectory.open(Paths.get("C:\\Users\\clillo\\Downloads\\lucene-8.2.0\\demo\\index"));
            IndexReader reader = DirectoryReader.open(dir);
        	IndexSearcher idxSearcher = new IndexSearcher(reader);
 
            TopDocs docs = idxSearcher.search(query, 20);
            System.out.println("length of top docs: " + docs.scoreDocs.length);
            for (ScoreDoc doc : docs.scoreDocs) {
                Document thisDoc = idxSearcher.doc(doc.doc);
                String r = "lillo"; 
                String m= thisDoc.get("apellido");
				int s = LevenshteinDistance.getDefaultInstance().apply(r, m);
				double largoMaximo = Math.max(r.length(), m.length())*1.0;
				
				int porcentaje = (int)((1.0 - s/largoMaximo) * 100.0);
                System.out.println(doc.doc + "\t" + thisDoc.get("apellido") + "\t" + doc.score+"\t"+porcentaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
 
    public static void  searchFuzzyQuery() {
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("apellido", "lillo"));
        searchIndexAndDisplayResults(fuzzyQuery );
    }

	public static void main(String[] args) throws Exception {
//		   IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("C:\\Users\\clillo\\Downloads\\lucene-8.2.0\\demo\\index")));
//		   IndexSearcher searcher = new IndexSearcher(reader);
//		   Analyzer analyzer = new StandardAnalyzer();
//		   
//		   QueryParser parser = new QueryParser("contents", analyzer);
//		  
//		   String line = "body:\"lillo\"~4";
//		   Query query = parser.parse(line);
//		   
//		   String str = "foo bar";
//		   String id = "123456";
//		   BooleanQuery bq = new BooleanQuery();
//		   Query query = qp.parse(str);
//		   bq.add(query, BooleanClause.Occur.MUST);
//		   bq.add(new TermQuery(new Term("id", id), BooleanClause.Occur.MUST_NOT);
//		   
//		   System.out.println("Searching for: " + query.toString("contents"));
		   
//		   searcher.search(query, 100);
//		   TopDocs results = searcher.search(query, 5 * 100);
//		   ScoreDoc[] hits = results.scoreDocs;
//		   System.out.println(hits.length);
		
		searchFuzzyQuery();
//        IndexSearcher searcher = createSearcher();
//        
//        
//        //Search by firstName
//        TopDocs foundDocs2 = searchByFirstName("borquez", searcher);
//         
//        System.out.println("Total Results :: " + foundDocs2.totalHits);
//         
//        for (ScoreDoc sd : foundDocs2.scoreDocs)
//        {
//            Document d = searcher.doc(sd.doc);
//            System.out.println(String.format(d.get("apellido")) + "\t" + sd.score);
//        }
	}
}
