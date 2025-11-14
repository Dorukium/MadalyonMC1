/*
 * Hello Minecraft! Launcher
 * Copyright (C) 2025  huangyuhui <huanghongxun2008@126.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.madalyonmc.launcher.ui.terracotta;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import com.madalyonmc.launcher.game.LauncherHelper;
import com.madalyonmc.launcher.setting.Profile;
import com.madalyonmc.launcher.setting.Profiles;
import com.madalyonmc.launcher.terracotta.TerracottaMetadata;
import com.madalyonmc.launcher.ui.Controllers;
import com.madalyonmc.launcher.ui.FXUtils;
import com.madalyonmc.launcher.ui.SVG;
import com.madalyonmc.launcher.ui.animation.ContainerAnimations;
import com.madalyonmc.launcher.ui.animation.TransitionPane;
import com.madalyonmc.launcher.ui.construct.AdvancedListBox;
import com.madalyonmc.launcher.ui.construct.PageAware;
import com.madalyonmc.launcher.ui.construct.TabHeader;
import com.madalyonmc.launcher.ui.decorator.DecoratorAnimatedPage;
import com.madalyonmc.launcher.ui.decorator.DecoratorPage;
import com.madalyonmc.launcher.ui.main.MainPage;
import com.madalyonmc.launcher.ui.versions.Versions;
import com.madalyonmc.launcher.util.Lang;
import com.madalyonmc.launcher.util.StringUtils;

import static com.madalyonmc.launcher.util.i18n.I18n.i18n;

public class TerracottaPage extends DecoratorAnimatedPage implements DecoratorPage, PageAware {
    private final ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(State.fromTitle(i18n("terracotta.terracotta")));
    private final TabHeader tab;
    private final TabHeader.Tab<TerracottaControllerPage> statusPage = new TabHeader.Tab<>("statusPage");
    private final TransitionPane transitionPane = new TransitionPane();

    @SuppressWarnings("unused")
    private ChangeListener<String> instanceChangeListenerHolder;

    public TerracottaPage() {
        statusPage.setNodeSupplier(TerracottaControllerPage::new);
        tab = new TabHeader(statusPage);
        tab.select(statusPage);

        transitionPane.setContent(statusPage.getNode(), ContainerAnimations.NONE);
        FXUtils.onChange(tab.getSelectionModel().selectedItemProperty(), newValue -> {
            transitionPane.setContent(newValue.getNode(), ContainerAnimations.FADE);
        });

        BorderPane left = new BorderPane();
        FXUtils.setLimitWidth(left, 200);
        VBox.setVgrow(left, Priority.ALWAYS);
        setLeft(left);

        AdvancedListBox sideBar = new AdvancedListBox()
                .addNavigationDrawerTab(tab, statusPage, i18n("terracotta.status"), SVG.TUNE);
        left.setTop(sideBar);

        AdvancedListBox toolbar = new AdvancedListBox()
                .addNavigationDrawerItem(i18n("version.launch"), SVG.ROCKET_LAUNCH, () -> {
                    Profile profile = Profiles.getSelectedProfile();
                    Versions.launch(profile, profile.getSelectedVersion(), LauncherHelper::setKeep);
                }, item -> {
                    instanceChangeListenerHolder = FXUtils.onWeakChangeAndOperate(Profiles.selectedVersionProperty(),
                            instanceName -> item.setSubtitle(StringUtils.isNotBlank(instanceName) ? instanceName : i18n("version.empty"))
                    );

                    MainPage mainPage = Controllers.getRootPage().getMainPage();
                    FXUtils.onScroll(item, mainPage.getVersions(), list -> {
                        String currentId = mainPage.getCurrentGame();
                        return Lang.indexWhere(list, instance -> instance.getId().equals(currentId));
                    }, it -> mainPage.getProfile().setSelectedVersion(it.getId()));
                })
                .addNavigationDrawerItem(i18n("terracotta.feedback.title"), SVG.FEEDBACK, () -> FXUtils.openLink(TerracottaMetadata.FEEDBACK_LINK))
                .addNavigationDrawerItem(i18n("terracotta.easytier"), SVG.HOST, () -> FXUtils.openLink("https://easytier.cn/"));
        BorderPane.setMargin(toolbar, new Insets(0, 0, 12, 0));
        left.setBottom(toolbar);

        setCenter(transitionPane);
    }

    @Override
    public void onPageShown() {
        tab.onPageShown();
    }

    @Override
    public void onPageHidden() {
        tab.onPageHidden();
    }

    @Override
    public ReadOnlyObjectProperty<State> stateProperty() {
        return state.getReadOnlyProperty();
    }
}
