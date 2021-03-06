package ex07;

import java.util.*;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.owl.elements.*;
import org.semanticweb.kaon2.api.owl.axioms.*;
import org.semanticweb.kaon2.api.reasoner.*;
import org.semanticweb.kaon2.server.rmi.*;

/**
 * This example shows how to access the ontology server. You should familiarize yourself
 * with Example 1 before proceeding with this example.
 */
public class Example07 {
    public static void main(String[] args) throws Exception {
        // The first thing we need to do is to start the server. For your convenience,
        // the accompaniying ANT build file contains a target ex07server for starting the server.
        // The main class of the server is org.semanticweb.kaon2.server.ServerMain. The class can
        // take the following parameters:
        //   -h                         prints the help message
        //   -registry                  starts the RMI registry in the server JVM (useful if you don't like having multiple processes open)
        //   -registryport              specifies the port of the RMI registry
        //   -rmi                       starts the RMI connector of KAON2
        //   -dig                       starts the DIG connector of KAON2
        //   -digport <n>               specifies the port of the DIG connector
        //   -ontologies <directory>    the directory containing the ontologies
        //
        // Initially the server does not contain any ontologies. Clients use ontology URIs to request ontologies
        // to be open. When such a request arrives to the server, the registry must somehow translate the ontology
        // URI into a physical URI to be able to open the actual ontology file. The server does this through
        // a special ontology resolver. It is possible programmatically to register a custom ontology resolver.
        // However, it is possible to start the server through the command line (an example is supplied in the ANT script).
        // Then, it is possible to specify the directory (using the -ontologies parameter) which contains registered
        // ontologies. Similarly, new ontologies are placed into this directory. To register or unregister ontologies,
        // simply drop them into the directory.
        System.out.println("Please make sure that the server is started.");
        System.out.println("For instructions on starting the server, please see the source of Example 07.");


        // A connection is initialized as usual. Furthermore, on the client side an ontology resolver is needed
        // in order to translate ontology URIs into physical URIs referencing an ontology at a server.
        // Physical URIs of ontologies at a server take the following form:
        //
        //     kaon2rmi://<host>[:<port>]?<ontologyURI>
        //
        // Scheme kaon2rmi is used to specify that the access is by RMI to a remote KAON2 server,
        // <host> is the name of the server host, <port> is the port (and can be omitted) and
        // <ontologyURI> is the ontology URI of the ontology that the client wants to acces.
        OntologyManager ontologyManager=KAON2Manager.newOntologyManager();
        DefaultOntologyResolver resolver=new DefaultOntologyResolver();
        resolver.registerReplacement("http://kaon2.semanticweb.org/example01","kaon2rmi://localhost?http://kaon2.semanticweb.org/example01");
        resolver.registerReplacement("http://kaon2.semanticweb.org/example01-inc","kaon2rmi://localhost?http://kaon2.semanticweb.org/example01-inc");
        ontologyManager.setOntologyResolver(resolver);

        // We open the ontology by simply invoking its ontology URI. Notice that opening the
        // 'http://kaon2.semanticweb.org/example01' ontology requires two translation steps:
        //
        // 1. Using the above created ontology resolver, the client translates the ontology URI
        //    'http://kaon2.semanticweb.org/example01' into the physical URI 'kaon2rmi://localhost?http://kaon2.semanticweb.org/example01'.
        //    This URI is then used to access the ontology at the server.
        // 2. The server parses the phyical URI and extracts the ontology URI 'http://kaon2.semanticweb.org/example01'
        //    (by looking at the part after the questionmark). The server then consults its own ontology resolver
        //    (initialized through the file passed to the server at startup) to translate this URI into a physical URI
        //    which points to the actual file.
        Ontology ontology=ontologyManager.openOntology("http://kaon2.semanticweb.org/example01",new HashMap<String,Object>());

        // One can use the ontology in exactly the same was as before. The client should not care
        // about the fact that this the ontology is at the server.
        OWLClass document=KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example01#document");
        Set<Description> subDescriptions=document.getSubDescriptions(ontology);
        System.out.println("The subdescriptions of '"+document.getURI()+"' are:");
        for (Description subDescription : subDescriptions)
            System.out.println("    "+subDescription.toString());
        System.out.println();

        // One can change the ontology as usual. If another client connects to the server and opens
        // the same ontology, he will see all the previous changes. To see this, try running the client
        // several times in a row. Each time, the client will add a new subclass, and you should see
        // how the list of subclasses grows.
        OWLClass projectProposal=KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example01#project-proposal"+((int)(Math.random()*1000)));
        SubClassOf axiom=KAON2Manager.factory().subClassOf(projectProposal,document);
        List<OntologyChangeEvent> list=new ArrayList<OntologyChangeEvent>();
        list.add(new OntologyChangeEvent(axiom,OntologyChangeEvent.ChangeType.ADD));
        System.out.println("Adding a subclass '"+projectProposal.getURI()+"' to '"+document.getURI()+"'.");
        System.out.println();
        ontology.applyChanges(list);

        // If you now shutdown the server, the changes will be lost, since the ontology has not been saved 
        //  at the server. The server will attempt to save ontologies before terminating, but this may not
        // work always. However, you may yourself request the server to store the ontology.
        ontology.persist();
        System.out.println("The ontology was persisted at the server.");
        // Go now into the server directory and open the file "example01.xml": it should contain new axioms. 
        
        // To show that the above statements indeed did change something, we again print out
        // the subdescriptions of 'http://kaon2.semanticweb.org/example01#document'.
        subDescriptions=document.getSubDescriptions(ontology);
        System.out.println("The subdescriptions of '"+document.getURI()+"' after change are:");
        for (Description subDescription : subDescriptions)
            System.out.println("    "+subDescription.toString());
        System.out.println();

        // After usage, we need to close the connection. Otherwise, we risk a resource leak.
        // In case of an ontology server, closing the connection is even more important, since this
        // releases some resources at the server itself. If connections are not closed properly, it
        // might happen that the server runs out of resources.
        ontologyManager.close();

        // Creating an ontology is cone in the same way as in the local case. Notice that you
        // need to tell your local resolver to place the ontology on the server. This can easily
        // be done by registering <ontologyURI> with the physical URI kaon2rmi://host:port?<ontologyURI>.
        ontologyManager=KAON2Manager.newOntologyManager();
        resolver=new DefaultOntologyResolver();
        resolver.registerReplacement("http://my.self.com/test","kaon2rmi://localhost?http://my.self.com/test");
        ontologyManager.setOntologyResolver(resolver);

        // If you are using a default ontology resolver at the server, the ontology will be placed into
        // the directory which you specidied using -ontologies parameter.
        // If you let the server run, the following call will fail the second time you invoke the example.
        // The reason for this is that the test ontology already exists at the server.
        try {
            ontology=ontologyManager.createOntology("http://my.self.com/test",new HashMap<String,Object>());
        }
        catch (KAON2Exception e) {
            System.out.println("Ontology 'http://my.self.com/test' could not be created at the server (probably because it already exists there).");
            System.exit(0);
        }
        System.out.println("Ontology 'http://my.self.com/test' successfully created at the server!");
        
        // We may now add new axioms to the ontology.
        list.clear();
        OWLClass cat=KAON2Manager.factory().owlClass("http://my.self.com/test#Cat"); 
        OWLClass animal=KAON2Manager.factory().owlClass("http://my.self.com/test#Animal");
        Individual garfield=KAON2Manager.factory().individual("http://my.self.com/test#Garfield");
        SubClassOf catsAreAnimals=KAON2Manager.factory().subClassOf(cat,animal);
        list.add(new OntologyChangeEvent(catsAreAnimals,OntologyChangeEvent.ChangeType.ADD));
        ClassMember garfieldIsCat=KAON2Manager.factory().classMember(cat,garfield);
        list.add(new OntologyChangeEvent(garfieldIsCat,OntologyChangeEvent.ChangeType.ADD));
        ontology.applyChanges(list);
        
        // Again, do not forget to tell the server to remember your ontology!
        ontology.persist();
        System.out.println();
        System.out.println("Ontology 'http://my.self.com/test' successfully persisted at the server!");
        // After this point, the axioms have been saved into the file in the server directory.
        
        // To obtain a list of ontologies registered at the server, use the following API.
        String[] ontologyURIs=RMIServerHelper.getAvailableOntologyURIs("localhost",-1);
        System.out.println();
        System.out.println("The server currently contains the following ontologies:");
        for (String ontologyURI : ontologyURIs)
            System.out.println("    "+ontologyURI);

        // Reasoning tasks may be performed in the same way as for local ontologies: by invoking
        // Ontology.createReasoner() you obtain a reasoner which you can use in the standard way.
        Reasoner reasoner=ontology.createReasoner();
        Query query=reasoner.createQuery(Namespaces.INSTANCE,"SELECT ?x WHERE { ?x rdf:type <http://my.self.com/test#Animal>}");
        System.out.println();
        System.out.println("The list of all Animal instances:");
        query.open();
        while (!query.afterLast()) {
            System.out.println("    "+query.tupleBuffer()[0].toString());
            query.next();
        }
        query.close();
        query.dispose();
        
        // For server ontologies it is even more important to release the reasoner after you are doe with it!
        reasoner.dispose();
        
        // Don't forget to clean-up!
        ontologyManager.close();
    }
}

