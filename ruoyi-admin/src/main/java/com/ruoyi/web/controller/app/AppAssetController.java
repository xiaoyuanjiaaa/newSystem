package com.ruoyi.web.controller.app;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.entity.AppAsset;
import com.ruoyi.system.service.IAppAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【防疫物资】Controller
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
@RestController
@RequestMapping("/appAsset")
public class AppAssetController extends BaseController
{
    @Autowired
    private IAppAssetService appAssetService;

    /**
     * 查询【防疫物资】列表
     */
    @PreAuthorize("@ss.hasPermi('system:asset:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppAsset appAsset)
    {
        startPage();
        List<AppAsset> list = appAssetService.selectAppAssetList(appAsset);
        return getDataTable(list);
    }

    /**
     * 导出【防疫物资】列表
     */
    @PreAuthorize("@ss.hasPermi('system:asset:export')")
    @Log(title = "【防疫物资】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppAsset appAsset)
    {
        List<AppAsset> list = appAssetService.selectAppAssetList(appAsset);
        ExcelUtil<AppAsset> util = new ExcelUtil<AppAsset>(AppAsset.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【防疫物资】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:asset:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(appAssetService.selectAppAssetById(id));
    }

    /**
     * 新增【防疫物资】
     */
    @PreAuthorize("@ss.hasPermi('system:asset:add')")
    @Log(title = "【防疫物资】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppAsset appAsset)
    {
        return toAjax(appAssetService.insertAppAsset(appAsset));
    }

    /**
     * 修改【防疫物资】
     */
    @PreAuthorize("@ss.hasPermi('system:asset:edit')")
    @Log(title = "【防疫物资】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppAsset appAsset)
    {
        return toAjax(appAssetService.updateAppAsset(appAsset));
    }

    /**
     * 删除【防疫物资】
     */
    @PreAuthorize("@ss.hasPermi('system:asset:remove')")
    @Log(title = "【防疫物资】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(appAssetService.deleteAppAssetByIds(ids));
    }
}
