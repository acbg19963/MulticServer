import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    ActividadService,
    ActividadPopupService,
    ActividadComponent,
    ActividadDetailComponent,
    ActividadDialogComponent,
    ActividadPopupComponent,
    ActividadDeletePopupComponent,
    ActividadDeleteDialogComponent,
    actividadRoute,
    actividadPopupRoute,
} from './';

const ENTITY_STATES = [
    ...actividadRoute,
    ...actividadPopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActividadComponent,
        ActividadDetailComponent,
        ActividadDialogComponent,
        ActividadDeleteDialogComponent,
        ActividadPopupComponent,
        ActividadDeletePopupComponent,
    ],
    entryComponents: [
        ActividadComponent,
        ActividadDialogComponent,
        ActividadPopupComponent,
        ActividadDeleteDialogComponent,
        ActividadDeletePopupComponent,
    ],
    providers: [
        ActividadService,
        ActividadPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticActividadModule {}
