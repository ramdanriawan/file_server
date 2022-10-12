package com.biru.web;

import com.biru.ReBrokerConstants;
import com.biru.ReBrokerConstantsRamdan.REST;
import com.biru.ReBrokerConstantsRamdan;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
import com.biru.service.CommonService;
import com.biru.service.CommonServiceRamdan;
import com.biru.view.ViewInqTr0006Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(REST.GENERAL)
public class GeneralRamdanController {

    @Autowired
    private CommonServiceRamdan commonService;

    @Autowired
    private EmailUtils emailUtils;

    private Logger logger = LoggerFactory.getLogger(GeneralRamdanController.class);

    @RequestMapping(value = REST.TR0006_INQUIRY_VIR, method = RequestMethod.POST)
	public @ResponseBody
	Object tr0006InquiryVir(@RequestBody Map<String, Object> param) {

		return commonService.tr0006InquiryVir(param);
	}

    @RequestMapping(value = REST.G_VOUCHER_ID, method = RequestMethod.POST)
    public List<DropdownIdText> tr0006GetVoucherId() {
        List<DropdownIdText> dropdownIdTexts = new ArrayList<>();

        for (ViewInqTr0006Entity tr0006Entity : commonService.tr0006GetVoucherId()) {

            try {

                if (dropdownIdTexts.stream().noneMatch(item -> item.getId().equals(tr0006Entity.getTrxVoucherId()))) {

                    dropdownIdTexts.add(new DropdownIdText(tr0006Entity.getTrxVoucherId(), tr0006Entity.getTrxVoucherId()));
                }
            } catch (Exception e) {
                break;
            }
        }

        return dropdownIdTexts;
    }


}
