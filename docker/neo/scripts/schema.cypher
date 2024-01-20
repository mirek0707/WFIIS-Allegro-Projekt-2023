CREATE (
art:News
{
  created: datetime("2024-01-04T18:40:32.142+0100"),
  title:"Sancho w Borussii",
  content: "Jadon Sancho w styczniowym oknie transferowym zamieni Manchester United na Borussię Dortmund. Angielski skrzydłowy uda się na wypożyczenie do BVB do końca sezonu 2023/2024.",
  author: "Mark Goldbridge"
}
)
CREATE (manu:Tag { name:"Manchester United" })
CREATE (bor:Tag { name:"Borussia Dortmund" })
CREATE (sancho:Tag { name:"Jadon Sancho" })

CREATE (art)-[:TAGGED_BY{ }]->(manu)
CREATE (art)-[:TAGGED_BY{ }]->(bor)
CREATE (art)-[:TAGGED_BY{ }]->(sancho)

CREATE (manu)-[:IS_TAGGING{ }]->(art)
CREATE (bor)-[:IS_TAGGING{ }]->(art)
CREATE (sancho)-[:IS_TAGGING{ }]->(art)