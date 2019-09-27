package com.previred.lucene;

import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.previred.rezagos.dao.ArchivosLocalesDAO;
import com.previred.rezagos.dao.IMacDao;
import com.previred.rezagos.dao.MacDaoArchivo;
import com.previred.rezagos.dto.ElementoIdDTO;
import com.previred.rezagos.dto.TiposElemento;
import com.previred.utils.formato.Formatos;

public class IndexFiles {

	private IndexFiles() {
	}

	/** Index all text files under a directory. */
	public static void main(String[] args) {
		long tiempo = System.currentTimeMillis();

		try {
			System.out.println("Indexing");
			ArchivosLocalesDAO daoArchivos = new ArchivosLocalesDAO();
			List<ElementoIdDTO> listaApellidosMac = daoArchivos.obtieneTodosLosApellidosMac(TiposElemento.apellidos);
						
			Directory dir = FSDirectory.open(Paths.get("C:\\Users\\clillo\\Downloads\\lucene-8.2.0\\demo\\index"));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);

			IndexWriter writer = new IndexWriter(dir, iwc);
			
			int n=0;
			for(ElementoIdDTO str: listaApellidosMac) {
				Document doc = new Document();
				StringField s1 = new StringField("apellido", str.getValor(), Field.Store.YES); 
				doc.add(s1);
				writer.addDocument(doc);

				if (n%10000==0)
					System.out.println("Apellidos " + Formatos.obtieneNumero(n) + " en " + Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));

				n++;
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
		
		System.out.println("Tiempo total del proceso: "+ Formatos.obtieneTiempo(System.currentTimeMillis() - tiempo));
	}
}
