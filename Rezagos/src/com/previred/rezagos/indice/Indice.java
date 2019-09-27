package com.previred.rezagos.indice;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.previred.rezagos.dto.ElementoIdDTO;

public class Indice {
	
	private static final String DIRECTORIO = "index";

	public static void crearIndice(List<ElementoIdDTO> listaValores) throws IOException {
		Directory dir = FSDirectory.open(Paths.get(DIRECTORIO));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);

		IndexWriter writer = new IndexWriter(dir, iwc);
		
		for(ElementoIdDTO to: listaValores) {
			Document doc = new Document();
			StringField s1 = new StringField("valor", to.getValor(), Field.Store.YES);
			doc.add(s1);
			StringField s2 = new StringField("id", to.getId(), Field.Store.YES); 
			doc.add(s2);
			writer.addDocument(doc);
		}
		
		writer.close();		
	}
	
	public static IndexSearcher obtenerBuscadorIndice() {
		IndexSearcher idxSearcher = null;
		try {
			Directory dir = FSDirectory.open(Paths.get(DIRECTORIO));
			IndexReader reader = DirectoryReader.open(dir);
			idxSearcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return idxSearcher;
	}
}
