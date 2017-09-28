/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipEstudianteDetailComponent } from '../../../../../../main/webapp/app/entities/tip-estudiante/tip-estudiante-detail.component';
import { TipEstudianteService } from '../../../../../../main/webapp/app/entities/tip-estudiante/tip-estudiante.service';
import { TipEstudiante } from '../../../../../../main/webapp/app/entities/tip-estudiante/tip-estudiante.model';

describe('Component Tests', () => {

    describe('TipEstudiante Management Detail Component', () => {
        let comp: TipEstudianteDetailComponent;
        let fixture: ComponentFixture<TipEstudianteDetailComponent>;
        let service: TipEstudianteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [TipEstudianteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipEstudianteService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipEstudianteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipEstudianteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipEstudianteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TipEstudiante(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tipEstudiante).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
