SELECT CURRENT TIMESTAMP FROM SYSIBM.SYSDUMMY1;

with CloseArea(zipcode, shape) as
(
	select Z1.zipcode, Z1.shape from cse532.zcta Z1, cse532.zcta Z2
	where Z2.zipcode='11735' and DB2GSE.ST_Intersects(Z1.shape, Z2.shape)=1
)

select distinct TF.FACILITY_NAME
from cse532.TRIFacility TF, CloseArea CA
where DB2GSE.ST_Contains(CA.shape, TF.geolocation)=1;

SELECT CURRENT TIMESTAMP FROM SYSIBM.SYSDUMMY1;
