import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    PlanetaService,
    PlanetaPopupService,
    PlanetaComponent,
    PlanetaDetailComponent,
    PlanetaDialogComponent,
    PlanetaPopupComponent,
    PlanetaDeletePopupComponent,
    PlanetaDeleteDialogComponent,
    planetaRoute,
    planetaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...planetaRoute,
    ...planetaPopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlanetaComponent,
        PlanetaDetailComponent,
        PlanetaDialogComponent,
        PlanetaDeleteDialogComponent,
        PlanetaPopupComponent,
        PlanetaDeletePopupComponent,
    ],
    entryComponents: [
        PlanetaComponent,
        PlanetaDialogComponent,
        PlanetaPopupComponent,
        PlanetaDeleteDialogComponent,
        PlanetaDeletePopupComponent,
    ],
    providers: [
        PlanetaService,
        PlanetaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticPlanetaModule {}
