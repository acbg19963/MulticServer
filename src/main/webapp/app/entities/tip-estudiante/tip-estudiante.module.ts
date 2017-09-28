import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import {
    TipEstudianteService,
    TipEstudiantePopupService,
    TipEstudianteComponent,
    TipEstudianteDetailComponent,
    TipEstudianteDialogComponent,
    TipEstudiantePopupComponent,
    TipEstudianteDeletePopupComponent,
    TipEstudianteDeleteDialogComponent,
    tipEstudianteRoute,
    tipEstudiantePopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipEstudianteRoute,
    ...tipEstudiantePopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TipEstudianteComponent,
        TipEstudianteDetailComponent,
        TipEstudianteDialogComponent,
        TipEstudianteDeleteDialogComponent,
        TipEstudiantePopupComponent,
        TipEstudianteDeletePopupComponent,
    ],
    entryComponents: [
        TipEstudianteComponent,
        TipEstudianteDialogComponent,
        TipEstudiantePopupComponent,
        TipEstudianteDeleteDialogComponent,
        TipEstudianteDeletePopupComponent,
    ],
    providers: [
        TipEstudianteService,
        TipEstudiantePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticTipEstudianteModule {}
