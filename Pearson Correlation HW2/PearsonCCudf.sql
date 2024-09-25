CREATE OR REPLACE PROCEDURE myavg_initialize(OUT sum_x DOUBLE, OUT sum_y DOUBLE, OUT sum_xy DOUBLE, OUT sum_x2 DOUBLE, OUT sum_y2 DOUBLE, OUT count INT)
LANGUAGE SQL
CONTAINS SQL
BEGIN

  SET sum_x = 0;
  SET sum_y = 0;
  SET sum_xy = 0;
  SET sum_x2 = 0;
  SET sum_y2 = 0;
  SET count = 0;

END @

CREATE OR REPLACE PROCEDURE myavg_accumulate(IN input1 DOUBLE, IN input2 DOUBLE, 
  INOUT sum_x DOUBLE, INOUT sum_y DOUBLE, INOUT sum_xy DOUBLE, INOUT sum_x2 DOUBLE, INOUT sum_y2 DOUBLE, INOUT count INT)
LANGUAGE SQL
CONTAINS SQL
BEGIN

  SET sum_x = sum_x + input1;
  SET sum_y = sum_y + input2;
  SET sum_xy = sum_xy + input1 * input2;
  SET sum_x2 = sum_x2 + input1 * input1;
  SET sum_y2 = sum_y2 + input2 * input2;
  SET count = count + 1;

END @

CREATE OR REPLACE PROCEDURE myavg_merge(IN sum_x DOUBLE, IN sum_y DOUBLE, IN sum_xy DOUBLE, IN sum_x2 DOUBLE, IN sum_y2 DOUBLE, IN count INT,
INOUT merge_sum_x DOUBLE, INOUT merge_sum_y DOUBLE, INOUT merge_sum_xy DOUBLE, INOUT merge_sum_x2 DOUBLE, INOUT merge_sum_y2 DOUBLE, INOUT merge_count INT)
LANGUAGE SQL
CONTAINS SQL
BEGIN

  SET merge_sum_x = sum_x + merge_sum_x;
  SET merge_sum_y = sum_y + merge_sum_y;
  SET merge_sum_xy = sum_xy + merge_sum_xy;
  SET merge_sum_x2 = sum_x2 + merge_sum_x2;
  SET merge_sum_y2 = sum_y2 + merge_sum_y2;
  SET merge_count = count + merge_count;

END @

CREATE OR REPLACE FUNCTION myavg_finalize(sum_x DOUBLE, sum_y DOUBLE, sum_xy DOUBLE, sum_x2 DOUBLE, sum_y2 DOUBLE, count INT)
LANGUAGE SQL
CONTAINS SQL
RETURNS DOUBLE
BEGIN

  RETURN ((count * sum_xy) - (sum_x * sum_y))/(sqrt(((count * sum_x2) - (sum_x * sum_x)) * ((count * sum_y2) - (sum_y * sum_y))));

END @

CREATE OR REPLACE FUNCTION myavg(DOUBLE, DOUBLE)
RETURNS DOUBLE
AGGREGATE WITH (sum_x DOUBLE, sum_y DOUBLE, sum_xy DOUBLE, sum_x2 DOUBLE, sum_y2 DOUBLE, count INT)
USING
INITIALIZE PROCEDURE myavg_initialize
ACCUMULATE PROCEDURE myavg_accumulate
MERGE PROCEDURE myavg_merge
FINALIZE FUNCTION myavg_finalize
@


