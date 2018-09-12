/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SbnzTestModule } from '../../../test.module';
import { DiseaseDetailComponent } from '../../../../../../main/webapp/app/entities/disease/disease-detail.component';
import { DiseaseService } from '../../../../../../main/webapp/app/entities/disease/disease.service';
import { Disease } from '../../../../../../main/webapp/app/entities/disease/disease.model';

describe('Component Tests', () => {

    describe('Disease Management Detail Component', () => {
        let comp: DiseaseDetailComponent;
        let fixture: ComponentFixture<DiseaseDetailComponent>;
        let service: DiseaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiseaseDetailComponent],
                providers: [
                    DiseaseService
                ]
            })
            .overrideTemplate(DiseaseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiseaseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiseaseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Disease(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.disease).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
