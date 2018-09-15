/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { DiagnosisDetailComponent } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis-detail.component';
import { DiagnosisService } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.service';
import { Diagnosis } from '../../../../../../main/webapp/app/entities/diagnosis/diagnosis.model';

describe('Component Tests', () => {

    describe('Diagnosis Management Detail Component', () => {
        let comp: DiagnosisDetailComponent;
        let fixture: ComponentFixture<DiagnosisDetailComponent>;
        let service: DiagnosisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnosisDetailComponent],
                providers: [
                    DiagnosisService
                ]
            })
            .overrideTemplate(DiagnosisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiagnosisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiagnosisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Diagnosis(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.diagnosis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
