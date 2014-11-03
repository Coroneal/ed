select bloguser.name, sum(tmp.comments)
from bloguser,
( select text.author_id , count(comment.id) as comments
from text,comment where text.id = comment.text_id
group by text.author_id ) as tmp
where bloguser.id = tmp.author_id
group by bloguser.name order by sum(tmp.comments) desc

-- zliczanie
select count(comment) from comment;
select count(text) from text;
select count(tag) from tag;

