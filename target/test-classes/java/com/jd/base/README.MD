

    /**
     *
     * searcher 调用链
     * {@link IndexSearcher#search(Query, int)}
     * {@link IndexSearcher#searchAfter(ScoreDoc, Query, int)}
     * {@link IndexSearcher#search(Query, CollectorManager)}
     * {@link IndexSearcher#search(Query, CollectorManager)}
     * {@link IndexSearcher#search(List, Weight, Collector)}
     *
     *
     * @throws ParseException
     */

    /**
     * {@link IndexWriterConfig}
     * {@link Analyzer}
     * {@link Directory}
     * {@link }
     * {@link FSDirectory} three class implements,  {@link org.apache.lucene.store.SimpleFSDirectory}, {@link org.apache.lucene.store.MMapDirectory}, {@link org.apache.lucene.store.NIOFSDirectory}
     * {@link FSDirectory#open} auto choose best fsdDirectory
     *
     *
     * {@link org.apache.lucene.index.LiveIndexWriterConfig}
     * merge相关
     * {@link org.apache.lucene.index.MergeScheduler}
     * {@link org.apache.lucene.index.MergePolicy}
     *
     * flush
     * {@link org.apache.lucene.index.FlushPolicy}
     * {@link org.apache.lucene.index.LiveIndexWriterConfig#setRAMBufferSizeMB}
     * {@link org.apache.lucene.index.LiveIndexWriterConfig#setMaxBufferedDocs}
     * {@link IndexWriterConfig#DEFAULT_RAM_BUFFER_SIZE_MB}
     *
     * @return
     */
     */