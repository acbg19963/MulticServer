/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AyudaDetailComponent } from '../../../../../../main/webapp/app/entities/ayuda/ayuda-detail.component';
import { AyudaService } from '../../../../../../main/webapp/app/entities/ayuda/ayuda.service';
import { Ayuda } from '../../../../../../main/webapp/app/entities/ayuda/ayuda.model';

describe('Component Tests', () => {

    describe('Ayuda Management Detail Component', () => {
        let comp: AyudaDetailComponent;
        let fixture: ComponentFixture<AyudaDetailComponent>;
        let service: AyudaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [AyudaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AyudaService,
                    JhiEventManager
                ]
            }).overrideTemplate(AyudaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AyudaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AyudaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Ayuda(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.ayuda).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
