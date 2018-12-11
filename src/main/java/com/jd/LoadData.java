package com.jd;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoadData {
    File file;

    {
        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("car.txt").getFile());
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public List<Document> readDocs() throws IOException {

        List<Document> docs = new ArrayList<>();

        List<String> list = FileUtils.readLines(file);

        List<String []> newData = list.stream().map((t) -> t.replaceAll("'", "").replaceAll(" ", "")).map((t) -> t.split(",")).filter((t) -> t.length == 7).collect(Collectors.toList());
        for(String [] ss: newData){

            Field id, brandId, modelName, modelPrefix, modelId, brandName, brandPrefix;

            Document doc = new Document();

            id = new LongPoint("id", Long.parseLong(ss[0]));
            brandId = new LongPoint("brandId", Long.parseLong(ss[1]));
            modelName = new StringField("modelName", ss[2], Field.Store.YES);
            modelPrefix = new StringField("modelPrefix", ss[3], Field.Store.YES);
            modelId = new LongPoint("modelId", Long.parseLong(ss[4]));
            brandName = new StringField("brandName", ss[5], Field.Store.YES);
            brandPrefix = new StringField("brandPrefix", ss[6], Field.Store.YES);

            doc.add(id);
            doc.add(brandId);
            doc.add(modelName);
            doc.add(modelPrefix);
            doc.add(modelId);
            doc.add(brandName);
            doc.add(brandPrefix);
            docs.add(doc);
        }

        return docs;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public List<Document> readDocs2() throws IOException {

        List<Document> docs = new ArrayList<>();

        List<String> list = FileUtils.readLines(file);

        List<String []> newData = list.stream().map((t) -> t.replaceAll("'", "").replaceAll(" ", "")).map((t) -> t.split(",")).filter((t) -> t.length == 7).collect(Collectors.toList());
        for(String [] ss: newData){

            Field id, brandId, modelName, modelPrefix, modelId, brandName, brandPrefix;
            Field idStrore, brandIdStore, modelIdStore;

            Document doc = new Document();

            id = new LongPoint("id", Long.parseLong(ss[0]));
            idStrore = new StoredField("id", Long.parseLong(ss[0]));

            brandId = new LongPoint("brandId", Long.parseLong(ss[1]));
            brandIdStore = new StoredField("brandId", Long.parseLong(ss[1]));

            modelName = new TextField("modelName", ss[2], Field.Store.YES);
            modelPrefix = new TextField("modelPrefix", ss[3], Field.Store.YES);

            modelId = new LongPoint("modelId", Long.parseLong(ss[4]));
            modelIdStore = new StoredField("modelId", Long.parseLong(ss[4]));

            brandName = new TextField("brandName", ss[5], Field.Store.YES);
            brandPrefix = new TextField("brandPrefix", ss[6], Field.Store.YES);

            doc.add(id);
            doc.add(idStrore);

            doc.add(brandId);
            doc.add(brandIdStore);

            doc.add(modelName);
            doc.add(modelPrefix);

            doc.add(modelId);
            doc.add(modelIdStore);

            doc.add(brandName);
            doc.add(brandPrefix);
            docs.add(doc);
        }

        return docs;
    }
}
