ElasticSearch Types Docs
--------------------------

- Question
```
question {
	"question_id": { "type": "integer" },
	"title": { "type": "text" },
	"content": { "type": "text" },
	"keywords": { "type" : "keyword" }
}
```

- Solution
```
solution {
	"solution_id": { "type": "integer" },
	"title": { "type": "text" },
	"content": { "type": "text" },
	## NOT SURE YET -> "_parent": { "type": "question" }
}
```
