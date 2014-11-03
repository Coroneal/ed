select bloguser.name, sum(tmp.comments)
from bloguser,
( select text.author_id , count(comment.id) as comments
from text,comment where text.id = comment.text_id
group by text.author_id ) as tmp
where bloguser.id = tmp.author_id
group by bloguser.name order by sum(tmp.comments) desc

select count(comment) from comment;
select count(text) from text;
select count(tag) from tag;

select tmp.title, tmp.release_date, comment.post_date from comment,
(select text.title, text.id, text.release_date, count(comment) from text, comment
where text.id = comment.text_id
group by text.title, text.id, text.release_date
order by count(comment) desc limit 100 ) as tmp
where tmp.id = comment.text_id
order by tmp.title,comment.post_date desc

select to_char(tmp.release_date,'Mon') as mon,
       extract(year from tmp.release_date) as yyyy,
       count(tmp.title) as "articles", tmp.name
from (
	select bloguser.name, text.release_date, text.title from text, bloguser
	where text.author_id = bloguser.id
	order by bloguser.name, text.release_date
) as tmp
group by 2,1,4
order by 2,1,3
