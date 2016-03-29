# Introduction #

Following the discussion started at this thread in semanticweb answers I would like to highlight one of the [posts](http://answers.semanticweb.com/questions/3304/forward-vs-backward-chaining) that perfectly explains when and why you should use one kind of chaining or other.

# Comparison #

In engineering terms, for query-answering, the benefits between forward-chaining and backward-chaining can be reduced to a simple trade-off:

  * forward-chaining (~materialisation): precomputing and storing answers is suitable for:
    * frequently accessed data,
    * which are relatively static,
    * which are expensive to compute, and/or
    * which are small enough to efficiently store
  * backward-chaining (~query-rewriting): storing a minimal index from which answers can be computed on demand is more suitable where:
    * there is little scope for reuse of computed answers,
    * the answers are dynamic,
    * answers can be efficiently computed at runtime, and/or
    * the "answer space" is too large to materialise and store

A hybrid approach should then give the best of both worlds, materialising the inferences that are frequently accessed, static, and/or small, and supporting query-rewriting for inferences that are large, cheap to do at runtime, dynamic and/or infrequently accessed.

For example, many large-scale reasoners:

typically rely on materialisation to do the bulk of reasoning.
use backward-chaining to support stuff like reflexive owl:sameAs statements, or rdf:type rdfs:Resource/owl:Thing
use a hybrid approach for equality (to avoid ~quadratic materialisation: a maximum of n² inferences where n is the no. of input triples), where each set of "URI aliases" (identifiers related by owl:sameAs) is stored in a special index
one URI is chosen as a canonical identifier to represent all such aliases in the indexed data
queries are rewritten to use the chosen canonical identifer
results data can be (optionally as needed) expanded to use all combinations of identifiers.

Given a set of inference rules:

  * Forward chaining means applying rules in a forward direction: recursively applying the rules over data to generate more data (and applying the rules over that data... I have a member of po:Person... it must also be a member of foaf:Person... and so it must be a foaf:Agent and dc:Agent... and so...)
Backward chaining means applying rules in a backwards manner: taking a goal (e.g., a query) and recursively working backwards to find more data that can satisfy the goal (I'm looking for foaf:Agents... I should also look for dc:Agents and foaf:Persons and po:Persons...)

  * Materialisation means writing the results of forward-chaining down

  * Query-rewriting means directly rewriting/expanding queries (e.g., adding UNIONs to also look for dc:Agents and foaf:Persons and po:Persons...) using backward-chaining techniques

  * Materialisation ≠ Forward chaining

  * Query-rewriting ≠ Backward chaining

# Conclusions #

Taking into account that in the Semantic Web realm there are a lot of approaches to perform forward chain processes to calculate the closure of an ontology and dataset, e.g. Jacopo Urbani's works, in ROCAS we are following a backward chain approach in order to ease the dinamyc calculation of new facts and data. Currently we are focus on A-Box reasoning but T-Box reasoning should be added in further versions. The main aim of this approach is to provide a complete reasoner based on backwards techniques that can dinamically process large datasets given answers focusing only on the required part of datasets.


