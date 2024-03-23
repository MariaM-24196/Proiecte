#include <stdio.h>

struct Coord
{
	int x, y;
};

void main()
{
	Coord varf, * pVarf;
	varf.x = 0;
	varf.y = 1;
	pVarf = &varf;
	printf("Varful are coordonatele x = %d, y = %d",
		pVarf->x, pVarf->y);
}