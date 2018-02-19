package com.challenge.starwarsapi.common.repository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.challenge.starwarsapi.common.model.Sequence;

public abstract class NextSequenceRepository<T> {
	
	protected abstract Class<T> getPersistentClass();
	
	protected abstract MongoOperations getMongoOperations();
	
	// The name must match with Sequence.sequence field
	private static final String FIELD_NAME = "sequence";

	public long getNextSequence() {
		Sequence counter = getMongoOperations().findAndModify(getQuery(getPersistentClass().getSimpleName()), getUpdate(), getOptions(), Sequence.class);
		return counter.getSequence();
	}
	
	private Query getQuery(String seqName) {
		return query(where("_id").is(seqName));
	}
	
	private Update getUpdate() {
		return new Update().inc(FIELD_NAME, 1);
	}
	
	private FindAndModifyOptions getOptions() {
		return options().returnNew(true).upsert(true);
	}
}