for $coauthor1 in doc("JohnLHennessy.xml")//coauthors//co//na//@pid,
    $coauthor2 in  doc("DAPatterson.xml")//coauthors//co//na[@pid = $coauthor1]
    
return <author name="{$coauthor2}"/>