import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    ActividadxEstudianteService,
    ActividadxEstudiantePopupService,
    ActividadxEstudianteComponent,
    ActividadxEstudianteDetailComponent,
    ActividadxEstudianteDialogComponent,
    ActividadxEstudiantePopupComponent,
    ActividadxEstudianteDeletePopupComponent,
    ActividadxEstudianteDeleteDialogComponent,
    actividadxEstudianteRoute,
    actividadxEstudiantePopupRoute,
} from './';

const ENTITY_STATES = [
    ...actividadxEstudianteRoute,
    ...actividadxEstudiantePopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActividadxEstudianteComponent,
        ActividadxEstudianteDetailComponent,
        ActividadxEstudianteDialogComponent,
        ActividadxEstudianteDeleteDialogComponent,
        ActividadxEstudiantePopupComponent,
        ActividadxEstudianteDeletePopupComponent,
    ],
    entryComponents: [
        ActividadxEstudianteComponent,
        ActividadxEstudianteDialogComponent,
        ActividadxEstudiantePopupComponent,
        ActividadxEstudianteDeleteDialogComponent,
        ActividadxEstudianteDeletePopupComponent,
    ],
    providers: [
        ActividadxEstudianteService,
        ActividadxEstudiantePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticActividadxEstudianteModule {}
