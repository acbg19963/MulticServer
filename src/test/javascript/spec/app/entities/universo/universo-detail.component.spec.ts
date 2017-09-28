/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MulticTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UniversoDetailComponent } from '../../../../../../main/webapp/app/entities/universo/universo-detail.component';
import { UniversoService } from '../../../../../../main/webapp/app/entities/universo/universo.service';
import { Universo } from '../../../../../../main/webapp/app/entities/universo/universo.model';

describe('Component Tests', () => {

    describe('Universo Management Detail Component', () => {
        let comp: UniversoDetailComponent;
        let fixture: ComponentFixture<UniversoDetailComponent>;
        let service: UniversoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MulticTestModule],
                declarations: [UniversoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UniversoService,
                    JhiEventManager
                ]
            }).overrideTemplate(UniversoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UniversoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UniversoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Universo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.universo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
