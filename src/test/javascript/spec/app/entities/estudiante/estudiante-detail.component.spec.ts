/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EstudianteDetailComponent } from '../../../../../../main/webapp/app/entities/estudiante/estudiante-detail.component';
import { EstudianteService } from '../../../../../../main/webapp/app/entities/estudiante/estudiante.service';
import { Estudiante } from '../../../../../../main/webapp/app/entities/estudiante/estudiante.model';

describe('Component Tests', () => {

    describe('Estudiante Management Detail Component', () => {
        let comp: EstudianteDetailComponent;
        let fixture: ComponentFixture<EstudianteDetailComponent>;
        let service: EstudianteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [EstudianteDetailComponent],
                providers: [
                    JhiDataUtils,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EstudianteService,
                    JhiEventManager
                ]
            }).overrideTemplate(EstudianteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstudianteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstudianteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Estudiante(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.estudiante).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
