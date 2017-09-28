/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActividadDetailComponent } from '../../../../../../main/webapp/app/entities/actividad/actividad-detail.component';
import { ActividadService } from '../../../../../../main/webapp/app/entities/actividad/actividad.service';
import { Actividad } from '../../../../../../main/webapp/app/entities/actividad/actividad.model';

describe('Component Tests', () => {

    describe('Actividad Management Detail Component', () => {
        let comp: ActividadDetailComponent;
        let fixture: ComponentFixture<ActividadDetailComponent>;
        let service: ActividadService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [ActividadDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActividadService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActividadDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActividadDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActividadService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Actividad(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.actividad).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
