/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActividadxEstudianteDetailComponent } from '../../../../../../main/webapp/app/entities/actividadx-estudiante/actividadx-estudiante-detail.component';
import { ActividadxEstudianteService } from '../../../../../../main/webapp/app/entities/actividadx-estudiante/actividadx-estudiante.service';
import { ActividadxEstudiante } from '../../../../../../main/webapp/app/entities/actividadx-estudiante/actividadx-estudiante.model';

describe('Component Tests', () => {

    describe('ActividadxEstudiante Management Detail Component', () => {
        let comp: ActividadxEstudianteDetailComponent;
        let fixture: ComponentFixture<ActividadxEstudianteDetailComponent>;
        let service: ActividadxEstudianteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [ActividadxEstudianteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActividadxEstudianteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActividadxEstudianteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActividadxEstudianteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActividadxEstudianteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ActividadxEstudiante(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.actividadxEstudiante).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
