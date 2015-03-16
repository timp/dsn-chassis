# Designing Web-based Systems for Scientific Data-Sharing Networks #

## Summary ##

This is a discussion document, highlighting challenges, requirements and vision for the design of Web-based information systems to support scientific data-sharing networks, primarily focused on assisting the global campaign to eliminate malaria.

TODO

## Background ##

### Centre for Genomics and Global Health ###

The [Centre for Genomics and Global Health (CGGH)](http://cggh.org), a joint research programme of Oxford University and the Wellcome Trust Sanger Institute, is tasked with providing support for data-sharing networks that enable clinicians and researchers around the world to collaborate effectively on large-scale research projects.

### MalariaGEN ###

The largest data-sharing network that CGGH currently supports is [MalariaGEN](http://malariagen.net), the Malaria Genomic Epidemiology Network. MalariaGEN is a partnership of researchers in 21 countries who are using genomic epidemiology to understand how protective immunity against malaria works, which is a fundamental problem in malaria vaccine development. MalariaGEN has been operational since 2008.

CGGH acts as the MalariaGEN Resource Centre, providing scientific and operational support for MalariaGEN's research and training activities. A key aspect of this operational support is the design, development and hosting of Web-based information systems that are used by MalariaGEN to manage data shared by MalariaGEN's research partners. CGGH previously developed and currently hosts a system called **Topheno**, which is the system used by MalariaGEN to manage data sharing. Many lessons have been learned in the development and use of Topheno, and much of the discussion in this document is drawn from that experience.

### WWARN ###

A second data-sharing network that CGGH supports is [WWARN](http://wwarn.org), the World-Wide Antimalarial Resistance Network. WWARN is a global collaboration working to ensure that anyone affected by malaria receives effective and safe drug treatment. WWARN's aim is to provide quality-assured intelligence, based on the balance of currently-available scientific data, to track the emergence of malarial drug resistance. WWARN is due to begin operations in the first half of 2010.

CGGH has responsibility for WWARN's scientific informatics module, which includes in its scope the design, development and hosting of Web-based information systems to support WWARN's data-sharing operations. These systems are currently under development.

### Common Features of WWARN and MalariaGEN ###

There are some key similarities between WWARN and MalariaGEN.

In both cases, the operational workflow begins with the submission of original research data, usually by a researcher who is/was involved in the study from which the data originates, acting from their host institution (usually a university).

In both cases, data are submitted to the network from a distributed community of researchers. In the case of MalariaGEN, the set of researchers submitting data to MalariaGEN is delimited by the set of partners who have signed up to one of MalariaGEN's [Consortial Projects](http://www.malariagen.net/home/science/consortial-projects.php). For WWARN, the set of researchers submitting data is envisaged to be slightly more open-ended, with researchers submitting any original data that is relevant to one of WWARN's [four scientific modules](http://www.wwarn.org/home/modules).

In both cases, data are **not** primarily captured for submission to MalariaGEN or WWARN, but are captured as part of an independently funded original research study. Each study from which data originates has its own scientific objectives, which may be related to the objectives of the data-sharing network, but if so are usually more specific and finer-grained. The subjects for each original study are usually drawn from at most a handful of locations within a single country. The data-sharing networks then work to aggregate the data from many independent studies, in a reliable and scientifically valid manner, to conduct coarser-grained analyses across larger scales of time, space and biology than are considered in any one original study.

This last point has a number of important consequences. For example, because data are being primarily captured for an original research study, and not for the data-sharing network's secondary analyses, the network is not in a position to mandate the manner or format of data collection and representation. Data may be collected for a range of purposes using different means a diversity of representations. The data-sharing network must learn to deal with this heterogeneity, and this forms a large part of the network's data management operations.

Also, because the data-sharing network is not the primary-endpoint for the data, those involved in the secondary analysis of shared data typically have to cajole researchers into submitting their data, because doing so means time out from their primary research activities. Therefore, the data-sharing network wants to minimise the obstacles it presents to those submitting data, and to find ways in which they can add value for the submitters' primary research, even though that research will not be perfectly aligned with the secondary research activities and goals of the network as a whole.

### Other Data-Sharing Networks ###

In addition to MalariaGEN and WWARN, CGGH is also involved in supporting an informal network of researchers working on the malaria parasite (Plasmodium) genomes. Here the main focus is on generating and analysing detailed genome sequence data using next-generation sequencing technology, although there may also be a need to share and aggregate other, related data. Finally, CGGH is involved in the [UKCRC Modernising Medical Microbiology project](http://www.modmedmicro.ac.uk), which may involve management of data from a number of different sources, although some of these sources will have been collected for health reasons and not for primary research.

Thus, involvement in data-sharing networks is a fundamental feature of CGGH's activities. Although CGGH's involvement is far from limited to informatics, and also encompasses sample and data management, statistics, ethics and programme management, nevertheless a key responsibility is the development of Web-based information systems that support the operational activities of a data-sharing network. It is also worth noting that the development or extension of those systems is often the rate-limiting step in establishing a data-sharing network or enabling it to adapt to a new type of data or analysis.

### Generic Information Systems for Data-Sharing Networks ###

It is thus of urgent strategic importance to CGGH to identify those requirements for information systems that are common across these data-sharing networks. Once these requirements are understood, we need to identify a set of existing software and services that can be adopted and deployed to fulfil those common requirements. The underlying driver is to minimise the amount of time and effort spent on designing, developing and running common infrastructure, and thus make available as much effort as possible to deal with those requirements that are unique to the scientific activities of a particular data-sharing network.

This document tries to identify and discuss many of the key requirements that are known to be common at least between MalariaGEN and WWARN. The goal of this document is to provide a basis for finding and evaluating existing software and services, and for designing a reference architecture which provides the highest possible point of departure for developing information systems for each data-sharing network.

### Short-Term Objectives ###

TODO get wwarn operational