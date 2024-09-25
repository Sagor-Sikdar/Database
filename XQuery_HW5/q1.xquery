for $coauthor in doc("JohnLHennessy.xml")//coauthors//co//na
let $author := doc("JohnLHennessy.xml")//r/*[author=$coauthor]
where count($author) > 0
order by count($author) descending
return <author name="{$coauthor}" count="{count($author)}"/>