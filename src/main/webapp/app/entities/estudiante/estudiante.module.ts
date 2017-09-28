import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MulticSharedModule } from '../../shared';
import { MulticAdminModule } from '../../admin/admin.module';
import {
    EstudianteService,
    EstudiantePopupService,
    EstudianteComponent,
    EstudianteDetailComponent,
    EstudianteDialogComponent,
    EstudiantePopupComponent,
    EstudianteDeletePopupComponent,
    EstudianteDeleteDialogComponent,
    estudianteRoute,
    estudiantePopupRoute,
} from './';

const ENTITY_STATES = [
    ...estudianteRoute,
    ...estudiantePopupRoute,
];

@NgModule({
    imports: [
        MulticSharedModule,
        MulticAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EstudianteComponent,
        EstudianteDetailComponent,
        EstudianteDialogComponent,
        EstudianteDeleteDialogComponent,
        EstudiantePopupComponent,
        EstudianteDeletePopupComponent,
    ],
    entryComponents: [
        EstudianteComponent,
        EstudianteDialogComponent,
        EstudiantePopupComponent,
        EstudianteDeleteDialogComponent,
        EstudianteDeletePopupComponent,
    ],
    providers: [
        EstudianteService,
        EstudiantePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MulticEstudianteModule {}
