with extracted_table (buyer_zip, mme_sum)
as (select buyer_zip,
		   sum(mme) as mme_sum
    from cse532.dea_ny
    group by buyer_zip)
select buyer_zip as buyer_location,
	   mme_sum / newTable.pop as MME_Normalised,
	   rank() over(order by mme_sum / newTable.pop desc) as Rank
from extracted_table
inner join
cse532.zip as newTable
on buyer_zip = newTable.zip
where
newTable.pop is not null
and
newTable.pop > 0
limit 10;