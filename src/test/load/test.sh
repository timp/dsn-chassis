#ab -c 1 -n 100 -e questionnaire_1536_512_1.csv http://localhost:8080/manta/questionnaire/

#wget --auth-no-challenge --user=suki@example.org --password=bar --header="Content-Type: application/atom+xml" --post-file=study.atom http://localhost:8080/manta/atombeat/content/studies

#ab -A colin@example.org:bar -p study.atom  -T application/atom+xml -c 1  -n 100 -e study_1536_512_1.csv  http://localhost:8080/manta/atombeat/content/studies
#ab -A colin@example.org:bar -p study.atom  -T application/atom+xml -c 1  -n 100 -e study_1024_512_1.csv  http://localhost:8080/manta/atombeat/content/studies
#ab -A colin@example.org:bar -p study.atom  -T application/atom+xml -c 1  -n 100 -e study_512_512_1.csv  http://localhost:8080/manta/atombeat/content/studies
#ab -A colin@example.org:bar -p study.atom  -T application/atom+xml -c 1  -n 100 -e study_2048_512_1.csv  http://localhost:8080/manta/atombeat/content/studies

#wget --auth-no-challenge --user=suki@example.org --password=bar --header="Content-Type: application/atom+xml" --post-file=study-info.atom http://localhost:8080/manta/atombeat/content/study-info

ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 1  -n 1000 -e study-info_2048_512_1.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 2  -n 1000 -e study-info_2048_512_2.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 3  -n 1000 -e study-info_2048_512_3.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 4  -n 1000 -e study-info_2048_512_4.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 5  -n 1000 -e study-info_2048_512_5.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 6  -n 1000 -e study-info_2048_512_6.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 7  -n 1000 -e study-info_2048_512_7.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 8  -n 1000 -e study-info_2048_512_8.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 9  -n 1000 -e study-info_2048_512_9.csv  http://localhost:8080/manta/atombeat/content/study-info
ab -A colin@example.org:bar -p study-info.atom  -T application/atom+xml -c 10 -n 1000 -e study-info_2048_512_10.csv http://localhost:8080/manta/atombeat/content/study-info

perl join.pl
