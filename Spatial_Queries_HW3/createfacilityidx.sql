create index cse532.facilitygseidx on cse532.TRIFacility(geolocation) extend using db2gse.spatial_index(0.5, 0.5, 4);
runstats on table cse532.TRIFacility and indexes all;