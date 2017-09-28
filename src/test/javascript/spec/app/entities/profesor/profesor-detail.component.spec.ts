/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProfesorDetailComponent } from '../../../../../../main/webapp/app/entities/profesor/profesor-detail.component';
import { ProfesorService } from '../../../../../../main/webapp/app/entities/profesor/profesor.service';
import { Profesor } from '../../../../../../main/webapp/app/entities/profesor/profesor.model';

describe('Component Tests', () => {

    describe('Profesor Management Detail Component', () => {
        let comp: ProfesorDetailComponent;
        let fixture: ComponentFixture<ProfesorDetailComponent>;
        let service: ProfesorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [ProfesorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProfesorService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProfesorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfesorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Profesor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.profesor).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
