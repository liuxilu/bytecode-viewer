package the.bytecode.club.bytecodeviewer.gui.components;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import the.bytecode.club.bytecodeviewer.Configuration;
import the.bytecode.club.bytecodeviewer.GlobalHotKeys;
import the.bytecode.club.bytecodeviewer.resources.Resources;
import the.bytecode.club.bytecodeviewer.gui.components.listeners.PressKeyListener;
import the.bytecode.club.bytecodeviewer.gui.components.listeners.ReleaseKeyListener;
import the.bytecode.club.bytecodeviewer.gui.theme.LAFTheme;
import the.bytecode.club.bytecodeviewer.translation.Translation;
import the.bytecode.club.bytecodeviewer.translation.components.TranslatedJCheckBox;
import the.bytecode.club.bytecodeviewer.util.JTextAreaUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * Searching on an RSyntaxTextArea using swing highlighting
 *
 * @author Konloch
 * @since 6/25/2021
 */
public class SearchableRSyntaxTextArea extends RSyntaxTextArea
{
	private final RTextScrollPane scrollPane = new RTextScrollPane(this);
	private final JPanel searchPanel = new JPanel(new BorderLayout());
	private final JTextField searchInput = new JTextField();
	private final JCheckBox caseSensitiveSearch = new TranslatedJCheckBox("Exact", Translation.EXACT);
	private final JLabel titleHeader = new JLabel("");
	private final Color scrollBackground = new Color(0x3c3f41);
	private final Color scrollForeground = new Color(0x575859);
	private Runnable onCtrlS;
	
	public SearchableRSyntaxTextArea()
	{
		if(Configuration.lafTheme == LAFTheme.DARK)
		{
			//this fixes the white border on the jScrollBar panes
			scrollPane.getHorizontalScrollBar().setBackground(scrollBackground);
			scrollPane.getHorizontalScrollBar().setForeground(scrollForeground);
			scrollPane.getVerticalScrollBar().setBackground(scrollBackground);
			scrollPane.getVerticalScrollBar().setForeground(scrollForeground);
		}
		
		setAntiAliasingEnabled(true);
		
		scrollPane.setColumnHeaderView(searchPanel);
		
		JButton searchNext = new JButton();
		JButton searchPrev = new JButton();
		JPanel buttonPane = new JPanel(new BorderLayout());
		buttonPane.add(searchNext, BorderLayout.WEST);
		buttonPane.add(searchPrev, BorderLayout.EAST);
		searchNext.setIcon(Resources.nextIcon);
		searchPrev.setIcon(Resources.prevIcon);
		searchPanel.add(buttonPane, BorderLayout.WEST);
		searchPanel.add(searchInput, BorderLayout.CENTER);
		searchPanel.add(caseSensitiveSearch, BorderLayout.EAST);
		
		searchNext.addActionListener(arg0 -> search(searchInput.getText(), true, caseSensitiveSearch.isSelected()));
		searchPrev.addActionListener(arg0 -> search(searchInput.getText(), false, caseSensitiveSearch.isSelected()));
		
		searchInput.addKeyListener(new ReleaseKeyListener(keyEvent ->
		{
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
				search(searchInput.getText(), true, caseSensitiveSearch.isSelected());
		}));
		
		addKeyListener(new PressKeyListener(keyEvent ->
		{
			if ((keyEvent.getKeyCode() == KeyEvent.VK_F) && ((keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0))
				searchInput.requestFocus();
			
			if (onCtrlS != null && (keyEvent.getKeyCode() == KeyEvent.VK_S) && ((keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0))
			{
				onCtrlS.run();
				return;
			}
			
			GlobalHotKeys.keyPressed(keyEvent);
		}));
	}
	
	public void search(String search, boolean forwardSearchDirection, boolean caseSensitiveSearch)
	{
		JTextAreaUtils.search(this, search, forwardSearchDirection, caseSensitiveSearch);
	}
	
	public void highlight(String pattern, boolean caseSensitiveSearch)
	{
		JTextAreaUtils.highlight(this, pattern, caseSensitiveSearch);
	}
	
	public String getLineText(int line) {
		try {
			if (line < getLineCount()) {
				int start = getLineStartOffset(line);
				int end = getLineEndOffset(line);
				return getText(start, end - start).trim();
			}
		} catch (BadLocationException ignored) { }
		return "";
	}
	
	public void setOnCtrlS(Runnable onCtrlS)
	{
		this.onCtrlS = onCtrlS;
	}
	
	public RTextScrollPane getScrollPane()
	{
		return scrollPane;
	}
	
	public JPanel getSearchPanel()
	{
		return searchPanel;
	}
	
	public JTextField getSearchInput()
	{
		return searchInput;
	}
	
	public JCheckBox getCaseSensitiveSearch()
	{
		return caseSensitiveSearch;
	}
	
	public JLabel getTitleHeader()
	{
		return titleHeader;
	}
	
	public Runnable getOnCtrlS()
	{
		return onCtrlS;
	}
}
