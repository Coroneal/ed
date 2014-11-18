select author_id, id, title from text;

select b.name, t.title from bloguser b, text t where b.id=t.author_id;

select c.author_id, t.title from comment c, text t where t.id=c.text_id limit 10;

select c.author_id as source, t.author_id as target, count(*) from 
	comment c
	join text t on t.id = c.text_id
	where c.post_date = 
(select post_date from comment group by post_date order by count(*) desc limit 1)
group by t.author_id, c.author_id

select id, name from bloguser where id in (
select distinct c.author_id as target from 
	comment c
	join text t on t.id = c.text_id
	where c.post_date = 
(select post_date from comment group by post_date order by count(*) desc limit 1)
union
select distinct t.author_id as target from 
	comment c
	join text t on t.id = c.text_id
	where c.post_date = 
(select post_date from comment group by post_date order by count(*) desc limit 1))	;


select count(*) from text where text.release_date =  
(select release_date from text group by release_date order by count(*) desc limit 1);


select release_date, count(*) from text group by release_date order by release_date;

--kometarze do artykulu w czasie
select t.id, t.release_date, c.post_date, count(*) from
	text t 
	join comment c on t.id=c.text_id
	group by t.id, t.release_date, c.post_date

--ilosc dni, od zamieszczenia artykuly do konca zainteresowania nim


select t.id, t.release_date, t.title,  DATE_PART('day', max(c.post_date)::timestamp - t.release_date::timestamp)+1 as ontop from
	text t 
	join comment c on t.id=c.text_id 
	group by t.id, t.release_date 
	order by ontop desc
	



--srednia ilosc komentarzy per artykul dla dla danego autora
select (select count(id)::real from comment)/(select count(id)::real from bloguser)/(select (now()::date - (select min(post_date) from comment))::real)
select * from comment limit 1
select post_date, count(*) from comment group by post_date limit 1

--user/autor/all ktory "wzbudza najwieksze zainteresowanie" - czyli jego texty/kommentarze sa najczesciej komentowane
with aut (user_id, num) as (
	select author_id, count(*) from text group by author_id
),
com (user_id, num) as (
	select t.author_id, count(c.id)
	from comment c
	join text t on c.text_id = t.id
	group by t.author_id
)
select a.user_id, c.num::real / a.num::real as wynik, a.num as teksty, c.num as komentarze from 
aut a join com c on a.user_id = c.user_id  order by 2 desc limit 20

--komentzrze z najwieksza liczba lajkow
select likes_number from comment order by likes_number desc
select listening_number from text order by listening_number desc


--zaleznosc miedzy aktywnoscia na tuaw a nowymi modelami Apple'a - pewnie trza nowa tableke z datami nowych rzeczy



select  t.title,  DATE_PART('day', max(c.post_date)::timestamp - t.release_date::timestamp)+1 as ontop from
	text t 
	join comment c on t.id=c.text_id
	group by t.release_date, t.title


select u.id as user, t.author_id from text t,
	bloguser u join comment c on u.id=c.author_id
	where c.text_id = t.id
	order by u.id
	group by t.author_id, u.ID

select t.release_date, count(t.id) as texts, count(c.id) as comments, count(t.id)+count(c.id) as all_ from text t
	join comment c on t.release_date=c.post_date
	where release_date = '2012-07-25'
	group by t.release_date
	order by 2 desc
	limit 10

with texts (date, num) as(
select t.release_date, count(t.id) from text t group by t.release_date
),
comments (date, num) as(
select c.post_date, count(c.id) from comment c	group by c.post_date
)
select t.date, t.num as texts_, c.num as comms, t.num+c.num as all_ from texts t join comments c on t.date=c.date



select c.post_date, count(c.id) from comment c
	where c.post_date = '2012-07-25'
	group by c.post_date
	order by 2 desc
	limit 10

	