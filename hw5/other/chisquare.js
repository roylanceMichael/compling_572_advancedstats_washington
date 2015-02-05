function pochisq(x, df) {
var a, y, s;
var e, c, z;
var even;

var LOG_SQRT_PI = 0.5723649429247000870717135; /* log(sqrt(pi)) */
var I_SQRT_PI = 0.5641895835477562869480795;   /* 1 / sqrt(pi) */
        
if (x <= 0.0 || df < 1) {
	return 1.0;
}
        
a = 0.5 * x;
even = !(df & 1);
if (df > 1) {
	y = ex(-a);
}

s = (even ? y : (2.0 * poz(-Math.sqrt(x))));
if (df > 2) {
	x = 0.5 * (df - 1.0);
	z = (even ? 1.0 : 0.5);

if (a > BIGX) {
	e = (even ? 0.0 : LOG_SQRT_PI);
	c = Math.log(a);

while (z <= x) {
	e = Math.log(z) + e;
	s += ex(c * z - a - e);
	z += 1.0;
}
	return s;
} else {
	e = (even ? 1.0 : (I_SQRT_PI / Math.sqrt(a)));
	c = 0.0;
while (z <= x) {
	e = e * (a / z);
	c = c + e;
	z += 1.0;
	}
	return c * y + s;
}
} else {
return s;
}
}