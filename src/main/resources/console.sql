select name,surname,birth_date,date_met,description
,status.title as status
, gender.title as gender
from friend
              join status on friend.status_id = status.id
              join gender on friend.gender_id = gender.id
               order by friend.id desc fetch first 2 rows only