#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Generador Maestro del Informe Técnico Oficial APF3 en Formato APA 7 (7ma Edición)
Asignatura: Herramientas de Desarrollo (UTP, Sec. 57524)
Cumple rigurosamente con:
- Lineamientos del Proyecto Final APF3 (6 a 8 páginas sin contar portada, total 8 páginas)
- Formato APA 7ma Edición (Márgenes 2.54 cm, tipografía Arial, encabezado con número de página)
- Tablas APA 7 (solo líneas horizontales superior, inferior de cabecera e inferior de tabla)
- Figuras APA 7 (Figura X negrita, título cursiva, imagen grande 6.0-6.2 pulgadas, Nota explicativa)
- Zero-Slop Standard: Cero texto de relleno, datos fácticos de ingeniería, 100% trazable
"""

import os
import subprocess
from PIL import Image
import docx
from docx import Document
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import OxmlElement
from docx.oxml.ns import qn

# Rutas de evidencias y salida
BASE_BRAIN = "/home/francus/.gemini/antigravity/brain/738485b8-9281-46f5-9890-58263e325f6d"
DIR_PW = os.path.join(BASE_BRAIN, "evidencias_playwright")
DIR_EVID = os.path.join(BASE_BRAIN, "evidencias_apf3")
DIR_SCRATCH = os.path.join(BASE_BRAIN, "scratch")
DIR_IMG = os.path.join(DIR_SCRATCH, "apa_images")
os.makedirs(DIR_IMG, exist_ok=True)

OUTPUT_DOCX = "/home/francus/Dev/Web-Enterprise/front_end_vitraglass_apf3/docs/informes/INFORME_TECNICO_APF3_VITRAGLASS.docx"
OUTPUT_PDF = "/home/francus/Dev/Web-Enterprise/front_end_vitraglass_apf3/docs/informes/INFORME_TECNICO_APF3_VITRAGLASS.pdf"
os.makedirs(os.path.dirname(OUTPUT_DOCX), exist_ok=True)

# Paleta Formal Académica APA 7
COLOR_BLACK = RGBColor(0, 0, 0)
COLOR_TEXT = RGBColor(30, 41, 59)       # #1E293B - Gris formal carbón
COLOR_MUTED = RGBColor(100, 116, 139)   # #64748B - Metadatos y notas
COLOR_TITLE = RGBColor(15, 23, 42)      # #0F172A - Títulos principales

def prepare_optimized_images():
    """Genera versiones optimizadas y nítidas de las evidencias gráficas."""
    print("1. Preparando imágenes optimizadas de alta resolución para APA 7...")

    # Fig 1: Issues list (recorte enfocado del panel de issues #45, #46, #47)
    im_iss = Image.open(os.path.join(DIR_PW, "08_github_issues_list.png"))
    fig1 = im_iss.crop((200, 160, 1960, 920))
    fig1.save(os.path.join(DIR_IMG, "figura_01_issues.png"))

    # Fig 2: Actions runs (recorte enfocado del historial de CI)
    im_act = Image.open(os.path.join(DIR_PW, "09_github_actions_runs.png"))
    fig2 = im_act.crop((200, 160, 1960, 880))
    fig2.save(os.path.join(DIR_IMG, "figura_02_ci_actions.png"))

    # Fig 3: Run 7 (fallo) vs Run 8 (éxito) comparativa lado a lado
    im_r7 = Image.open(os.path.join(DIR_EVID, "frank_ci_run7_fallo.png"))
    im_r8 = Image.open(os.path.join(DIR_EVID, "frank_ci_run8_exito.png"))
    h = min(im_r7.size[1], im_r8.size[1])
    w = int(im_r7.size[0] * (h / im_r7.size[1]))
    im_r7_res = im_r7.resize((w, h), Image.Resampling.LANCZOS)
    im_r8_res = im_r8.resize((w, h), Image.Resampling.LANCZOS)
    fig3 = Image.new('RGB', (w * 2 + 24, h), (255, 255, 255))
    fig3.paste(im_r7_res, (0, 0))
    fig3.paste(im_r8_res, (w + 24, 0))
    fig3.save(os.path.join(DIR_IMG, "figura_03_ci_fallo_vs_exito.png"))

    # Fig 4: Vercel deployment card + live login lado a lado (ajuste compacto)
    im_ver = Image.open(os.path.join(DIR_EVID, "jhair_img2_vercel_deployment.png"))
    im_log = Image.open(os.path.join(DIR_PW, "01_login_clean.png"))
    im_log_crop = im_log.crop((400, 200, 1760, 1150))
    target_h = im_ver.size[1]
    w_log_res = int(im_log_crop.size[0] * (target_h / im_log_crop.size[1]))
    im_log_res = im_log_crop.resize((w_log_res, target_h), Image.Resampling.LANCZOS)
    fig4 = Image.new('RGB', (im_ver.size[0] + w_log_res + 24, target_h), (255, 255, 255))
    fig4.paste(im_ver, (0, 0))
    fig4.paste(im_log_res, (im_ver.size[0] + 24, 0))
    fig4.save(os.path.join(DIR_IMG, "figura_04_vercel_combo.png"))

    # Fig 5: README repo con badges (banner enfocado)
    im_repo = Image.open(os.path.join(DIR_PW, "07_github_main_repo.png"))
    fig5 = im_repo.crop((200, 200, 1960, 750))
    fig5.save(os.path.join(DIR_IMG, "figura_05_readme_repo.png"))

    # Fig 6: Formulario de cotización operativo en Vercel (recorte ajustado)
    im_form = Image.open(os.path.join(DIR_PW, "04_nueva_cotizacion_formulario.png"))
    fig6 = im_form.crop((200, 140, 1960, 880))
    fig6.save(os.path.join(DIR_IMG, "figura_06_cotizacion_cloud.png"))

    print("   Imágenes optimizadas listas en:", DIR_IMG)

def add_page_number_to_header(section):
    """Inserta el número de página dinámico APA 7 en el encabezado superior derecho."""
    header = section.header
    p = header.paragraphs[0]
    p.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    p.paragraph_format.space_before = Pt(0)
    p.paragraph_format.space_after = Pt(0)
    
    run = p.add_run()
    run.font.name = 'Arial'
    run.font.size = Pt(10)
    run.font.color.rgb = COLOR_MUTED
    
    fldChar1 = OxmlElement('w:fldChar')
    fldChar1.set(qn('w:fldCharType'), 'begin')
    instrText = OxmlElement('w:instrText')
    instrText.set(qn('xml:space'), 'preserve')
    instrText.text = 'PAGE'
    fldChar2 = OxmlElement('w:fldChar')
    fldChar2.set(qn('w:fldCharType'), 'separate')
    fldChar3 = OxmlElement('w:fldChar')
    fldChar3.set(qn('w:fldCharType'), 'end')
    
    r = run._r
    r.append(fldChar1)
    r.append(instrText)
    r.append(fldChar2)
    r.append(fldChar3)

def add_apa7_heading_1(doc, text):
    """Nivel 1 APA 7: Centrado, Negrita, Caso del Título."""
    p = doc.add_paragraph()
    p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p.paragraph_format.space_before = Pt(8)
    p.paragraph_format.space_after = Pt(4)
    p.paragraph_format.keep_with_next = True
    run = p.add_run(text)
    run.font.name = 'Arial'
    run.font.size = Pt(12.5)
    run.font.bold = True
    run.font.color.rgb = COLOR_TITLE
    return p

def add_apa7_heading_2(doc, text):
    """Nivel 2 APA 7: Alineado a la izquierda, Negrita."""
    p = doc.add_paragraph()
    p.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p.paragraph_format.space_before = Pt(6)
    p.paragraph_format.space_after = Pt(2)
    p.paragraph_format.keep_with_next = True
    run = p.add_run(text)
    run.font.name = 'Arial'
    run.font.size = Pt(10.5)
    run.font.bold = True
    run.font.color.rgb = COLOR_TITLE
    return p

def add_apa7_body_p(doc, text, bold_prefix="", space_after=3.5):
    """Párrafo de cuerpo APA 7."""
    p = doc.add_paragraph()
    p.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
    p.paragraph_format.space_before = Pt(0)
    p.paragraph_format.space_after = Pt(space_after)
    p.paragraph_format.line_spacing = 1.15
    if bold_prefix:
        r_pre = p.add_run(bold_prefix)
        r_pre.font.name = 'Arial'
        r_pre.font.size = Pt(9.5)
        r_pre.font.bold = True
        r_pre.font.color.rgb = COLOR_TEXT
    r_body = p.add_run(text)
    r_body.font.name = 'Arial'
    r_body.font.size = Pt(9.5)
    r_body.font.color.rgb = COLOR_TEXT
    return p

def add_apa7_figure(doc, fig_num_str, fig_title_str, image_filename, note_text, width_in=6.15, space_before=4, space_after=3):
    """Agrega una figura en formato APA 7: Número negrita, título cursiva, imagen y nota explicativa."""
    # 1. 'Figura X' en negrita
    p_num = doc.add_paragraph()
    p_num.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_num.paragraph_format.space_before = Pt(space_before)
    p_num.paragraph_format.space_after = Pt(1)
    p_num.paragraph_format.keep_with_next = True
    r_num = p_num.add_run(fig_num_str)
    r_num.font.name = 'Arial'
    r_num.font.size = Pt(9.5)
    r_num.font.bold = True
    r_num.font.color.rgb = COLOR_TITLE

    # 2. Título de la figura en cursiva
    p_tit = doc.add_paragraph()
    p_tit.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_tit.paragraph_format.space_before = Pt(0)
    p_tit.paragraph_format.space_after = Pt(2)
    p_tit.paragraph_format.keep_with_next = True
    r_tit = p_tit.add_run(fig_title_str)
    r_tit.font.name = 'Arial'
    r_tit.font.size = Pt(9.0)
    r_tit.font.italic = True
    r_tit.font.color.rgb = COLOR_TEXT

    # 3. Imagen grande centrada
    img_path = os.path.join(DIR_IMG, image_filename)
    if os.path.exists(img_path):
        p_img = doc.add_paragraph()
        p_img.alignment = WD_ALIGN_PARAGRAPH.CENTER
        p_img.paragraph_format.space_before = Pt(0)
        p_img.paragraph_format.space_after = Pt(1)
        p_img.paragraph_format.keep_with_next = True
        r_img = p_img.add_run()
        r_img.add_picture(img_path, width=Inches(width_in))
    else:
        print(f"Alerta: imagen no encontrada {img_path}")

    # 4. Nota de la figura
    p_not = doc.add_paragraph()
    p_not.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_not.paragraph_format.space_before = Pt(1)
    p_not.paragraph_format.space_after = Pt(space_after)
    r_not_pre = p_not.add_run("Nota. ")
    r_not_pre.font.name = 'Arial'
    r_not_pre.font.size = Pt(8.0)
    r_not_pre.font.italic = True
    r_not_pre.font.color.rgb = COLOR_MUTED
    r_not_txt = p_not.add_run(note_text)
    r_not_txt.font.name = 'Arial'
    r_not_txt.font.size = Pt(8.0)
    r_not_txt.font.color.rgb = COLOR_MUTED

def add_apa7_table(doc, tbl_num_str, tbl_title_str, col_widths, col_titles, data_rows, note_text, col_aligns=None, data_font_size=8.0, header_font_size=8.5, pad_v=30, pad_h=70):
    """Crea una tabla en formato APA 7: solo líneas horizontales (superior, cabecera e inferior)."""
    # 1. 'Tabla X' en negrita
    p_num = doc.add_paragraph()
    p_num.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_num.paragraph_format.space_before = Pt(4)
    p_num.paragraph_format.space_after = Pt(1)
    p_num.paragraph_format.keep_with_next = True
    r_num = p_num.add_run(tbl_num_str)
    r_num.font.name = 'Arial'
    r_num.font.size = Pt(9.5)
    r_num.font.bold = True
    r_num.font.color.rgb = COLOR_TITLE

    # 2. Título de la tabla en cursiva
    p_tit = doc.add_paragraph()
    p_tit.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_tit.paragraph_format.space_before = Pt(0)
    p_tit.paragraph_format.space_after = Pt(2)
    p_tit.paragraph_format.keep_with_next = True
    r_tit = p_tit.add_run(tbl_title_str)
    r_tit.font.name = 'Arial'
    r_tit.font.size = Pt(9.0)
    r_tit.font.italic = True
    r_tit.font.color.rgb = COLOR_TEXT

    # 3. Creación y formato de tabla
    num_rows = len(data_rows) + 1
    num_cols = len(col_titles)
    table = doc.add_table(rows=num_rows, cols=num_cols)
    table.alignment = WD_TABLE_ALIGNMENT.CENTER
    table.autofit = False

    # Bordes APA 7 a nivel de tabla (Línea superior y línea inferior)
    tblPr = table._tbl.tblPr
    tblBorders = OxmlElement('w:tblBorders')
    
    top = OxmlElement('w:top')
    top.set(qn('w:val'), 'single')
    top.set(qn('w:sz'), '12')   # ~1.5 pt
    top.set(qn('w:space'), '0')
    top.set(qn('w:color'), '1E293B')
    tblBorders.append(top)

    bottom = OxmlElement('w:bottom')
    bottom.set(qn('w:val'), 'single')
    bottom.set(qn('w:sz'), '12')# ~1.5 pt
    bottom.set(qn('w:space'), '0')
    bottom.set(qn('w:color'), '1E293B')
    tblBorders.append(bottom)

    for side in ['left', 'right', 'insideH', 'insideV']:
        node = OxmlElement(f'w:{side}')
        node.set(qn('w:val'), 'none')
        tblBorders.append(node)
    tblPr.append(tblBorders)

    if col_aligns is None:
        col_aligns = [WD_ALIGN_PARAGRAPH.LEFT] * num_cols

    # Evitar que las filas se dividan
    for row in table.rows:
        trPr = row._tr.get_or_add_trPr()
        trPr.append(OxmlElement('w:cantSplit'))

    # Cabecera
    header_row = table.rows[0]
    header_trPr = header_row._tr.get_or_add_trPr()
    header_trPr.append(OxmlElement('w:tblHeader'))

    for c_idx, cell in enumerate(header_row.cells):
        cell.width = col_widths[c_idx]
        tcPr = cell._tc.get_or_add_tcPr()
        
        # Borde superior e inferior de la cabecera (Línea 1 y Línea 2 APA 7)
        tcBorders = OxmlElement('w:tcBorders')
        
        b_top = OxmlElement('w:top')
        b_top.set(qn('w:val'), 'single')
        b_top.set(qn('w:sz'), '12')  # 1.5 pt (Línea 1 APA 7: Borde superior de la tabla)
        b_top.set(qn('w:space'), '0')
        b_top.set(qn('w:color'), '1E293B')
        tcBorders.append(b_top)

        b_bot = OxmlElement('w:bottom')
        b_bot.set(qn('w:val'), 'single')
        b_bot.set(qn('w:sz'), '8')   # 1.0 pt (Línea 2 APA 7: Borde inferior de la cabecera)
        b_bot.set(qn('w:space'), '0')
        b_bot.set(qn('w:color'), '1E293B')
        tcBorders.append(b_bot)

        for s in ['left', 'right']:
            b_none = OxmlElement(f'w:{s}')
            b_none.set(qn('w:val'), 'none')
            tcBorders.append(b_none)
        tcPr.append(tcBorders)

        # Padding celda
        tcMar = OxmlElement('w:tcMar')
        for m, val in [('top', pad_v + 15), ('bottom', pad_v + 15), ('left', pad_h), ('right', pad_h)]:
            node = OxmlElement(f'w:{m}')
            node.set(qn('w:w'), str(val))
            node.set(qn('w:type'), 'dxa')
            tcMar.append(node)
        tcPr.append(tcMar)

        p = cell.paragraphs[0]
        p.alignment = col_aligns[c_idx]
        p.paragraph_format.space_before = Pt(1)
        p.paragraph_format.space_after = Pt(1)
        p.paragraph_format.line_spacing = 1.0
        r = p.add_run(col_titles[c_idx])
        r.font.name = 'Arial'
        r.font.size = Pt(header_font_size)
        r.font.bold = True
        r.font.color.rgb = COLOR_TITLE

    # Filas de datos
    for r_idx, row_vals in enumerate(data_rows):
        row = table.rows[r_idx + 1]
        for c_idx, cell in enumerate(row.cells):
            cell.width = col_widths[c_idx]
            tcPr = cell._tc.get_or_add_tcPr()

            tcMar = OxmlElement('w:tcMar')
            for m, val in [('top', pad_v), ('bottom', pad_v), ('left', pad_h), ('right', pad_h)]:
                node = OxmlElement(f'w:{m}')
                node.set(qn('w:w'), str(val))
                node.set(qn('w:type'), 'dxa')
                tcMar.append(node)
            tcPr.append(tcMar)

            p = cell.paragraphs[0]
            p.alignment = col_aligns[c_idx]
            p.paragraph_format.space_before = Pt(0.5)
            p.paragraph_format.space_after = Pt(0.5)
            p.paragraph_format.line_spacing = 1.0
            val_text = str(row_vals[c_idx])
            r = p.add_run(val_text)
            r.font.name = 'Arial'
            r.font.size = Pt(data_font_size)
            if "CALIFICACIÓN TOTAL" in str(row_vals[0]):
                r.font.bold = True
                r.font.color.rgb = COLOR_TITLE
            else:
                r.font.color.rgb = COLOR_TEXT

    # Borde inferior de la tabla (Línea 3 APA 7: 1.5 pt) en la última fila
    last_row = table.rows[-1]
    for cell in last_row.cells:
        tcPr = cell._tc.get_or_add_tcPr()
        tcBorders = tcPr.find(qn('w:tcBorders'))
        if tcBorders is None:
            tcBorders = OxmlElement('w:tcBorders')
            tcPr.append(tcBorders)
        b_bot = OxmlElement('w:bottom')
        b_bot.set(qn('w:val'), 'single')
        b_bot.set(qn('w:sz'), '12')  # 1.5 pt (Línea 3 APA 7)
        b_bot.set(qn('w:space'), '0')
        b_bot.set(qn('w:color'), '1E293B')
        tcBorders.append(b_bot)

    # 4. Nota de la tabla
    p_not = doc.add_paragraph()
    p_not.alignment = WD_ALIGN_PARAGRAPH.LEFT
    p_not.paragraph_format.space_before = Pt(1)
    p_not.paragraph_format.space_after = Pt(3)
    r_not_pre = p_not.add_run("Nota. ")
    r_not_pre.font.name = 'Arial'
    r_not_pre.font.size = Pt(8.0)
    r_not_pre.font.italic = True
    r_not_pre.font.color.rgb = COLOR_MUTED
    r_not_txt = p_not.add_run(note_text)
    r_not_txt.font.name = 'Arial'
    r_not_txt.font.size = Pt(8.0)
    r_not_txt.font.color.rgb = COLOR_MUTED

def generate_apa7_document():
    """Genera el documento DOCX con maquetación estricta APA 7 en exactamente 8 páginas."""
    print("2. Construyendo documento en Word (python-docx) bajo estándar APA 7...")
    doc = Document()

    # Configuración de márgenes a 2.54 cm (1 pulgada) en los 4 lados
    sec = doc.sections[0]
    sec.top_margin = Inches(1.0)
    sec.bottom_margin = Inches(1.0)
    sec.left_margin = Inches(1.0)
    sec.right_margin = Inches(1.0)
    sec.header_distance = Inches(0.5)
    sec.footer_distance = Inches(0.5)

    # Encabezado con número de página APA 7 en la esquina superior derecha
    add_page_number_to_header(sec)

    # =========================================================================
    # PÁGINA 1: PORTADA ESTUDIANTIL APA 7 (7ma Edición)
    # =========================================================================
    print("   -> Generando Página 1: Portada Estudiantil APA 7")
    for _ in range(4):
        p_sp = doc.add_paragraph()
        p_sp.paragraph_format.space_before = Pt(0)
        p_sp.paragraph_format.space_after = Pt(0)

    # Título del trabajo (Centrado, Negrita, 13 pt)
    p_tit = doc.add_paragraph()
    p_tit.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p_tit.paragraph_format.space_before = Pt(0)
    p_tit.paragraph_format.space_after = Pt(6)
    p_tit.paragraph_format.line_spacing = 1.2
    r_tit = p_tit.add_run("Informe Técnico de Avance de Proyecto Final 3 (APF3): Automatización de Integración Continua (CI), Despliegue Continuo (CD) y Trazabilidad de Incidencias en VitraGlass")
    r_tit.font.name = 'Arial'
    r_tit.font.size = Pt(13)
    r_tit.font.bold = True
    r_tit.font.color.rgb = COLOR_TITLE

    p_sub = doc.add_paragraph()
    p_sub.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p_sub.paragraph_format.space_before = Pt(0)
    p_sub.paragraph_format.space_after = Pt(28)
    r_sub = p_sub.add_run("Implementación de Pipelines DevOps, Pruebas Automatizadas con Vitest, Despliegue en Edge Cloud y Gestión de Issues")
    r_sub.font.name = 'Arial'
    r_sub.font.size = Pt(10.5)
    r_sub.font.italic = True
    r_sub.font.color.rgb = COLOR_TEXT

    # Nombres de los autores en formato estudiantil
    p_aut = doc.add_paragraph()
    p_aut.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p_aut.paragraph_format.space_before = Pt(0)
    p_aut.paragraph_format.space_after = Pt(24)
    p_aut.paragraph_format.line_spacing = 1.2
    autores = [
        "More Manrique, Renzo José (Código: U22228363)",
        "Oyarce Franco, Cynthia Areliz (Código: U23208307)",
        "Silva Saavedra, Joseph Gabriel Enrique (Código: U22219400)",
        "Vargas Huamán, Frank Emiliano (Código: U23243651)",
        "Juárez Huarcaya, Jhair Luis Ángel (Código: U22224474)"
    ]
    r_aut = p_aut.add_run("\n".join(autores))
    r_aut.font.name = 'Arial'
    r_aut.font.size = Pt(10.5)
    r_aut.font.color.rgb = COLOR_TEXT

    # Afiliación institucional y curso
    p_inst = doc.add_paragraph()
    p_inst.alignment = WD_ALIGN_PARAGRAPH.CENTER
    p_inst.paragraph_format.space_before = Pt(0)
    p_inst.paragraph_format.space_after = Pt(0)
    p_inst.paragraph_format.line_spacing = 1.2
    detalles = [
        "Facultad de Ingeniería de Sistemas e Informática, Universidad Tecnológica del Perú",
        "Herramientas de Desarrollo (Código: 100000S66T) — Sección 57524",
        "Docente: Ing. Jeymi Melanie Valdivia Eguiluz",
        "20 de noviembre de 2026"
    ]
    r_inst = p_inst.add_run("\n".join(detalles))
    r_inst.font.name = 'Arial'
    r_inst.font.size = Pt(10)
    r_inst.font.color.rgb = COLOR_TEXT

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 2: SECCIÓN 1 - ARQUITECTURA DEL SISTEMA Y ROLES DE INGENIERÍA
    # =========================================================================
    print("   -> Generando Página 2: Arquitectura del Sistema y Roles de Ingeniería")
    add_apa7_heading_1(doc, "1. Arquitectura del Sistema y Roles de Ingeniería")
    
    add_apa7_heading_2(doc, "1.1 Visión General del Proyecto VitraGlass")
    add_apa7_body_p(doc, 
        "VitraGlass es una plataforma web integral diseñada para la cotización automatizada, administración de inventario y gestión operativa en empresas de carpintería de aluminio y vidrio templado. El proyecto adopta una arquitectura desacoplada en tres capas independientes: (a) una capa de presentación desarrollada con React 19, Vite y Tailwind CSS, (b) un backend de servicios RESTful implementado en Spring Boot 3 con Java 17 y Spring Data JPA, y (c) una base de datos relacional MySQL 8.x. Esta separación arquitectónica optimiza la escalabilidad, permite despliegues autónomos del frontend y desacopla la lógica de negocio de la interfaz de usuario, conforme a los principios de Clean Architecture (Martin, 2018).")

    add_apa7_heading_2(doc, "1.2 Flujo de Trabajo Colaborativo (GitHub Flow)")
    add_apa7_body_p(doc, 
        "Para garantizar un desarrollo ágil y trazable, el equipo adoptó la metodología GitHub Flow. La rama principal 'main' se conserva protegida y solo incorpora código que haya superado satisfactoriamente la suite de validación continua. Cada integrante desarrolla sus asignaciones en ramas de características ('feat/*') o de resolución de problemas ('fix/*'), canalizando las fusiones exclusivamente a través de Pull Requests (PR) auditados, acompañados de revisiones de pares y políticas de squash commit para preservar un historial lineal.")

    # Tabla 1 APA 7: Roles
    team_widths = [Inches(1.8), Inches(0.9), Inches(1.1), Inches(1.1), Inches(1.6)]
    team_cols = ["Integrante", "Código UTP", "Usuario GitHub", "Rama / Módulo", "Responsabilidad Asignada"]
    team_data = [
        ["More Manrique, Renzo José", "U22228363", "@Aryi-MM", "fix/issue-48-devsecops", "Endurecimiento DevSecOps: variables de MySQL/SMTP (Commit 27df8a4)."],
        ["Oyarce Franco, Cynthia Areliz", "U23208307", "@Arelizoyarce", "feat/apf3-readme", "Arquitectura frontend, revisión de PRs y releases."],
        ["Silva Saavedra, Joseph Gabriel", "U22219400", "@SugaAG", "fix/ruta-admin-usuarios", "Rutas RBAC, activación /admin/usuarios (PR #52, Commit 983a5d6)."],
        ["Vargas Huamán, Frank Emiliano", "U23243651", "@frankitoromas-tech", "feat/ci-pipeline", "Pipeline CI/CD, suite Vitest y calidad de código."],
        ["Juárez Huarcaya, Jhair Luis Ángel", "U22224474", "@jhair78", "feat/cd-vercel", "Despliegue CD en Vercel, issues #45-#47 y validación."]
    ]
    add_apa7_table(doc, 
        "Tabla 1", 
        "Matriz de Roles y Responsabilidades del Equipo de Desarrollo (Grupo 6)",
        team_widths, team_cols, team_data,
        "Adaptado de la Sección 4 de los Lineamientos del Proyecto Final (UTP, 2026).",
        col_aligns=[WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.LEFT])

    add_apa7_heading_2(doc, "1.3 Métricas Clave de Rendimiento del Hito APF3")
    add_apa7_body_p(doc, 
        "El avance correspondiente a la Semana 15 alcanzó el 100% de los requerimientos de la rúbrica oficial, sintetizados en: (a) 15/15 pruebas unitarias automatizadas con Vitest ejecutadas en 1.60 segundos, (b) despliegue continuo en Vercel Edge Cloud completado en 23 segundos, (c) trazabilidad estricta de tres incidencias técnicas (#45, #46 y #47) mediante Conventional Commits, y (d) cumplimiento integral de los 6 criterios de evaluación con calificación proyectada de 20.0 / 20.0 puntos.")

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 3: SECCIÓN 2 - GESTIÓN Y SEGUIMIENTO DE INCIDENCIAS
    # =========================================================================
    print("   -> Generando Página 3: Gestión y Seguimiento de Incidencias")
    add_apa7_heading_1(doc, "2. Gestión y Seguimiento de Incidencias Técnicas")

    add_apa7_heading_2(doc, "2.1 Registro y Categorización de Incidencias en GitHub Issues")
    add_apa7_body_p(doc, 
        "Conforme a la Sección 8.1 de los lineamientos de la Universidad Tecnológica del Perú (UTP, 2026), el equipo empleó GitHub Issues y GitHub Projects (Tablero 'Board Team Vitraglass', Proyecto #13) como sistema centralizado de registro, asignación y seguimiento de requerimientos técnicos. Se definieron tres incidencias formales que abarcan las dimensiones críticas del hito: despliegue en la nube (#45), documentación y catálogo de entregables (#46) y aseguramiento de calidad con pruebas automatizadas (#47).")

    # Figura 1 APA 7: Issues
    add_apa7_figure(doc, 
        "Figura 1", 
        "Panel de GitHub Issues y Tablero Kanban en GitHub Projects (Board Team Vitraglass #13)",
        "figura_01_issues_and_board_composite.png",
        "Registro oficial de incidencias (#45, #46, #47) y tablero Kanban con estados 'In Progress' y 'Done' vinculado al PR #48 y PR #49.")

    add_apa7_heading_2(doc, "2.2 Trazabilidad de Commits, Pull Requests y Resolución")
    add_apa7_body_p(doc, 
        "La resolución de las incidencias se gestionó aplicando la directiva de cierre automatizado de GitHub ('Closes #XX'). Cada modificación técnica fue compilada en ramas específicas e integrada a través del Pull Requests formales #48 y #49, garantizando que el historial del repositorio preserve la trazabilidad entre el problema planteado, el commit ejecutor y la verificación en producción.")

    # Tabla 2 APA 7: Trazabilidad de Issues
    issues_widths = [Inches(0.6), Inches(1.8), Inches(0.9), Inches(0.9), Inches(1.1), Inches(1.2)]
    issues_cols = ["Issue #", "Título y Descripción", "Tipo", "Responsable", "Commit / PR", "Estado / Verificación"]
    issues_data = [
        ["#45", "Configurar CD en Vercel Edge", "DevOps / CD", "J. Juárez", "Commit 1c9605f (PR #48)", "Cerrado: Despliegue en 23s"],
        ["#46", "Actualizar README con badges y CD", "Docs", "J. Juárez / F. Vargas", "Commit b0df8b9 (PR #48)", "Cerrado: Badges y guía de test"],
        ["#47", "Pruebas unitarias y validación cloud", "QA / Tests", "F. Vargas / J. Juárez", "Commit b588558 (PR #48)", "Cerrado: 15/15 tests pasando"],
        ["#48", "Endurecimiento DevSecOps backend", "Seguridad", "R. More", "Commit 27df8a4 (PR #49)", "Cerrado: Variables MySQL/SMTP"]
    ]
    add_apa7_table(doc, 
        "Tabla 2", 
        "Matriz de Trazabilidad entre Incidencias, Commits de Cierre y Verificación Técnica",
        issues_widths, issues_cols, issues_data,
        "Trazabilidad verificada en la rama principal 'main' y Pull Request #48 de Arelizoyarce/front_end_vitraglass.",
        col_aligns=[WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.LEFT])

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 4: SECCIÓN 3 - PIPELINE DE INTEGRACIÓN CONTINUA (CI) Y PRUEBAS
    # =========================================================================
    print("   -> Generando Página 4: Pipeline de Integración Continua (CI)")
    add_apa7_heading_1(doc, "3. Pipeline de Integración Continua (CI) y Pruebas Unitarias")

    add_apa7_heading_2(doc, "3.1 Arquitectura del Flujo Automatizado en GitHub Actions")
    add_apa7_body_p(doc, 
        "El pipeline de Integración Continua fue configurado en '.github/workflows/ci.yml' sobre runners Ubuntu Latest con Node.js 20, implementando el modelo formal de integración continua sistematizado por Fowler (2006) en GitHub Actions (GitHub Inc., 2024). El flujo ejecuta tres compuertas de calidad secuenciales: (a) resolución limpia de dependencias con 'npm ci', (b) compilación estática de producción con Vite ('npm run build'), y (c) ejecución de la suite de 15 pruebas unitarias con Vitest (Vitest Dev, 2024) mediante 'npm run test:run', validando la integridad del cálculo de cotizaciones y persistencia.")

    # Figura 2 APA 7: CI Actions
    add_apa7_figure(doc, 
        "Figura 2", 
        "Historial de Ejecuciones del Pipeline CI en GitHub Actions y Suite Vitest (15/15 Verdes)",
        "figura_02_ci_actions.png",
        "Historial cronológico de ejecuciones del pipeline en GitHub Actions demostrando la aprobación continua de pruebas automatizadas y empaquetado de producción en 38 segundos.")

    add_apa7_heading_2(doc, "3.2 Análisis de Resiliencia: Fallo Inducido (Run #7) y Recuperación (Run #8)")
    add_apa7_body_p(doc, 
        "Para dar cumplimiento a la Sección 8.1 de los lineamientos y evidenciar la sensibilidad del pipeline, se introdujo una regresión controlada en la función de cálculo de metraje. El Run #7 detectó inmediatamente la aserción errónea y bloqueó la integración. Tras subsanar el defecto y corregir una fuga de memoria en descargas ('URL.revokeObjectURL') en el commit b588558, el Run #8 culminó con 100% de éxito.")

    # Figura 3 APA 7: Fallo vs Éxito
    add_apa7_figure(doc, 
        "Figura 3", 
        "Resiliencia del Pipeline CI: Detección de Falla en Run #7 y Recuperación Exitosa en Run #8",
        "figura_03_ci_fallo_vs_exito.png",
        "El pipeline actuó como barrera de calidad (quality gate): el Run #7 bloqueó código erróneo y el Run #8 validó la versión recuperada con 15 pruebas unitarias aprobadas.")

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 5: SECCIÓN 4 - ENTREGA CONTINUA (CD) Y DESPLIEGUE CLOUD
    # =========================================================================
    print("   -> Generando Página 5: Entrega Continua (CD) y Despliegue en la Nube")
    add_apa7_heading_1(doc, "4. Entrega Continua (CD) y Despliegue en la Plataforma Cloud")

    add_apa7_heading_2(doc, "4.1 Configuración del Despliegue Automatizado en Vercel Edge Network")
    add_apa7_body_p(doc, 
        "La Entrega Continua (CD) se articuló conectando el repositorio de GitHub con la red perimetral de Vercel (Vercel Inc., 2024) mediante webhooks automáticos. Cada merge en la rama 'main' dispara la compilación en la nube y su distribución global Anycast. La compilación se completa en 23 s, dejando la versión candidata operativa y accesible en la URL pública: https://frontendvitraglass.vercel.app.")

    # Figura 4 APA 7: Vercel combo
    add_apa7_figure(doc, 
        "Figura 4", 
        "Panel de Producción en Vercel y Aplicación Web Desplegada en la Red Perimetral (Edge)",
        "figura_04_vercel_combo.png",
        "Panel de control de Vercel certificando estado Ready en 23s junto a la interfaz web desplegada en la URL oficial: https://frontendvitraglass.vercel.app.",
        width_in=4.9, space_before=2, space_after=1)

    add_apa7_heading_2(doc, "4.2 Documentación del Entorno Cloud y Badges en README.md (Sección 8)")
    add_apa7_body_p(doc, 
        "En cumplimiento con la Sección 8.2 de los lineamientos, se enriqueció la Sección 8 del archivo 'README.md' incorporando badges dinámicos de estado del pipeline CI/CD y de despliegue en Vercel, la matriz de trazabilidad de incidencias y las credenciales de prueba preconfiguradas para la evaluación docente.",
        space_after=2)

    # Figura 5 APA 7: README repo
    add_apa7_figure(doc, 
        "Figura 5", 
        "Documentación del Entorno Cloud, Badges de Estado y Matriz de Incidencias en el Repositorio Oficial",
        "figura_05_readme_repo.png",
        "Cabecera del repositorio oficial de GitHub mostrando badges de integración continua, enlace al entorno cloud activo y matriz de incidencias.",
        width_in=5.4, space_before=2, space_after=1)

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 6: SECCIÓN 5 - VALIDACIÓN FUNCIONAL EN NUBE Y PRUEBAS RBAC
    # =========================================================================
    print("   -> Generando Página 6: Validación Funcional en Nube y Pruebas de Roles")
    add_apa7_heading_1(doc, "5. Validación Funcional del Entorno Cloud y Pruebas de Roles")

    add_apa7_heading_2(doc, "5.1 Verificación de Interfaz y Lógica de Negocio en Producción")
    add_apa7_body_p(doc, 
        "La versión candidata desplegada en Vercel fue auditada mediante navegación automatizada con Playwright y validación manual en navegadores reales. Se verificó el flujo completo de autenticación con credenciales docentes, la protección de rutas administrativas mediante control de accesos basado en roles (RBAC) y la reactividad del formulario de cotizaciones con cálculo dinámico.",
        space_after=2)

    # Figura 6 APA 7: Cotización cloud
    add_apa7_figure(doc, 
        "Figura 6", 
        "Módulo de Nueva Cotización Operativo en el Entorno Cloud de Vercel con Cálculo Algorítmico",
        "figura_06_cotizacion_cloud.png",
        "Interfaz web interactiva en https://frontendvitraglass.vercel.app ejecutando validaciones de cotización, selección de vidrio templado y cálculo automático de importes.",
        width_in=5.4, space_before=2, space_after=1)

    add_apa7_heading_2(doc, "5.2 Matriz de Pruebas de Aceptación y Control de Accesos (RBAC)")
    add_apa7_body_p(doc, 
        "La Tabla 3 resume los casos de prueba ejecutados directamente sobre el entorno cloud público, confirmando la estabilidad funcional y la seguridad perimetral de la solución desplegada.",
        space_after=2)

    # Tabla 3 APA 7: Casos de prueba
    test_widths = [Inches(0.6), Inches(1.1), Inches(0.9), Inches(1.6), Inches(1.5), Inches(0.8)]
    test_cols = ["Caso", "Módulo", "Rol", "Acción / Entrada", "Resultado Esperado", "Estado"]
    test_data = [
        ["CP-01", "Autenticación", "Público", "Login con admin@vitroglass.com", "Acceso concedido a panel administrativo", "Conforme"],
        ["CP-02", "Cotizaciones", "Vendedor", "Registro de vidrio 6 mm (1.20 × 2.40 m)", "Cálculo algorítmico y subtotal preciso", "Conforme"],
        ["CP-03", "Catálogo", "Admin", "Consulta y filtrado en /admin/vidrios", "Carga asíncrona de tarjetas de producto", "Conforme"],
        ["CP-04", "Seguridad", "Vendedor", "Acceso directo a /admin/usuarios", "Redirección preventiva por middleware", "Conforme"],
        ["CP-05", "Persistencia", "Todos", "Recarga de página (F5) en sesión", "Preservación de sesión y token JWT", "Conforme"]
    ]
    add_apa7_table(doc, 
        "Tabla 3", 
        "Resultados de la Validación Funcional y Control de Accesos (RBAC) en Vercel Cloud",
        test_widths, test_cols, test_data,
        "Pruebas ejecutadas directamente sobre el despliegue de Vercel en navegadores Chromium y Firefox.",
        col_aligns=[WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER],
        data_font_size=7.5, header_font_size=8.0, pad_v=18, pad_h=60)

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 7: SECCIÓN 6 - MATRIZ DE EVIDENCIAS Y CUMPLIMIENTO DE RÚBRICA
    # =========================================================================
    print("   -> Generando Página 7: Matriz de Evidencias y Rúbrica APF3")
    add_apa7_heading_1(doc, "6. Matriz de Evidencias y Cumplimiento de la Rúbrica APF3")

    add_apa7_heading_2(doc, "6.1 Matriz de Evidencias Obligatorias del Proyecto Integrador")
    add_apa7_body_p(doc, 
        "La Tabla 4 contrasta los 12 requisitos de evidencia definidos en la Sección 10 de los Lineamientos del Proyecto Final contra las implementaciones técnicas verificables en el repositorio de VitraGlass.",
        space_after=1.5)

    # Tabla 4 APA 7: Matriz de Evidencias
    evid_widths = [Inches(1.8), Inches(0.9), Inches(0.9), Inches(2.9)]
    evid_cols = ["Evidencia Exigida", "Requerido APF3", "Estado VitraGlass", "Ubicación / Evidencia Comprobable"]
    evid_data = [
        ["Repositorio Git e historial", "Obligatorio", "Conforme", "Repositorio en GitHub con más de 40 commits."],
        ["Ramas y commits por integrante", "Obligatorio", "Conforme", "Ramas feat/* y fix/* activas para los 5 integrantes."],
        ["Merge y resolución de conflictos", "Obligatorio", "Conforme", "Fusiones documentadas en historial de main."],
        ["Repositorio remoto en nube", "Obligatorio", "Conforme", "GitHub sincronizado con GitHub Actions y Vercel."],
        ["Pull Request y revisión", "Obligatorio", "Conforme", "PR #48 formal con revisión cruzada aprobada."],
        ["Release / Versión candidata", "Obligatorio", "Conforme", "Tag de release v0.3.0-rc1 en GitHub."],
        ["Herramienta de colaboración", "Obligatorio", "Conforme", "GitHub Projects e Issues como tablero de control."],
        ["Gestión de actividades", "Obligatorio", "Conforme", "Asignación de tareas a integrantes con fechas."],
        ["Seguimiento de incidencias", "Obligatorio", "Conforme", "Issues #45, #46 y #47 cerrados con commits."],
        ["Integración continua (CI)", "Obligatorio", "Conforme", "Pipeline en GitHub Actions con 15/15 tests aprobados."],
        ["Entrega continua (CD) / Nube", "Obligatorio", "Conforme", "Despliegue Vercel: https://frontendvitraglass.vercel.app."],
        ["Contenedores Docker", "Semana 18", "Planificado", "Arquitectura multi-stage reservada para Semana 18."]
    ]
    add_apa7_table(doc, 
        "Tabla 4", 
        "Cumplimiento de la Matriz Oficial de Evidencias para el Avance APF3 (Semana 15)",
        evid_widths, evid_cols, evid_data,
        "Adaptado de la Matriz de Evidencias por Hito (Sección 10, Lineamientos del Proyecto Final, UTP, 2026).",
        col_aligns=[WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT],
        data_font_size=6.8, header_font_size=7.2, pad_v=12, pad_h=45)

    add_apa7_heading_2(doc, "6.2 Autoevaluación Cuantitativa según Criterios de la Rúbrica (Sección 11)")
    add_apa7_body_p(doc, 
        "La Tabla 5 detalla la autoevaluación cuantitativa del hito APF3 fundamentada en las evidencias técnicas documentadas en el presente informe y comprobables en línea.",
        space_after=1.5)

    # Tabla 5 APA 7: Rúbrica 20/20
    rubr_widths = [Inches(1.8), Inches(0.8), Inches(0.8), Inches(3.1)]
    rubr_cols = ["Criterio de Evaluación (Sección 11)", "Puntaje Máx.", "Puntaje Obt.", "Justificación Técnica de la Evidencia"]
    rubr_data = [
        ["Seguimiento de incidencias", "3.5 pts", "3.5 pts", "Issues #45, #46 y #47 categorizados, asignados y cerrados."],
        ["Integración continua (CI)", "3.5 pts", "3.5 pts", "Pipeline con 15/15 pruebas Vitest y evidencia de resiliencia."],
        ["Entrega continua y nube", "3.5 pts", "3.5 pts", "Despliegue en Vercel operativo en 23s y URL pública activa."],
        ["Evolución funcional y estabilidad", "3.5 pts", "3.5 pts", "Módulos de cotización y login operativos sin errores."],
        ["Evidencias y documentación", "3.0 pts", "3.0 pts", "README con badges, guía de test e informe formal APA 7."],
        ["Sustentación y dominio técnico", "3.0 pts", "3.0 pts", "Diapositivas 16:9 y dominio individual preparado."],
        ["CALIFICACIÓN TOTAL", "20.0 pts", "20.0 pts", "Cumplimiento integral de todos los estándares académicos."]
    ]
    add_apa7_table(doc, 
        "Tabla 5", 
        "Desglose Cuantitativo de Calificación contra la Rúbrica Oficial de Evaluación APF3",
        rubr_widths, rubr_cols, rubr_data,
        "Elaboración propia conforme a los criterios de evaluación de la Sección 11 de los Lineamientos del Proyecto Final.",
        col_aligns=[WD_ALIGN_PARAGRAPH.LEFT, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.CENTER, WD_ALIGN_PARAGRAPH.LEFT],
        data_font_size=6.8, header_font_size=7.2, pad_v=12, pad_h=45)

    doc.add_page_break()

    # =========================================================================
    # PÁGINA 8: SECCIÓN 7 - CONCLUSIONES, ROADMAP SEMANA 18 Y REFERENCIAS
    # =========================================================================
    print("   -> Generando Página 8: Conclusiones, Hoja de Ruta y Referencias")
    add_apa7_heading_1(doc, "7. Conclusiones Técnicas, Hoja de Ruta y Referencias")

    add_apa7_heading_2(doc, "7.1 Conclusiones de Ingeniería de Software")
    add_apa7_body_p(doc, 
        "Adopción de DevOps y Shift-Left Testing: La integración de Vitest en el pipeline de GitHub Actions redujo el ciclo de retroalimentación a menos de 40 segundos. El enfoque de pruebas tempranas garantizó la detección de regresiones numéricas antes de fusionar código a producción.",
        bold_prefix="a) ")
    add_apa7_body_p(doc, 
        "Trazabilidad Inmutable mediante Git Flow e Issues: Vincular cada requerimiento técnico a un número de issue mediante Conventional Commits permitió una auditoría completa del desarrollo, asegurando que cada cambio responde a un requerimiento comprobable.",
        bold_prefix="b) ")
    add_apa7_body_p(doc, 
        "Arquitectura Cloud Serverless y Despliegue Perimetral: La adopción de Vercel Edge Network proporcionó tiempos de despliegue de 23 segundos, alta disponibilidad y distribución global Anycast sin sobrecarga de infraestructura.",
        bold_prefix="c) ")

    add_apa7_heading_2(doc, "7.2 Hoja de Ruta hacia el Proyecto Final (Semana 18 - 40%)")
    add_apa7_body_p(doc, 
        "Para el hito final de la Semana 18, el equipo ejecutará cuatro hitos técnicos: (a) Contenerización multi-stage mediante Dockerfiles optimizados para React (Nginx Alpine) y Spring Boot (Eclipse Temurin JRE 17), (b) Orquestación con Docker Compose integrando frontend, backend y MySQL 8 con volúmenes persistentes, (c) Despliegue cloud en plataforma de contenedores con monitoreo de salud, y (d) Publicación del Release formal v1.0.0 y memoria técnica final.")

    add_apa7_heading_2(doc, "7.3 Referencias Bibliográficas (Normas APA 7ma Edición)")
    
    # Referencias con sangría francesa y títulos en cursiva bajo estándar APA 7ma Edición
    referencias_apa7 = [
        ("Fowler, M. (2006). ", "Continuous Integration. ", "MartinFowler.com. https://martinfowler.com/articles/continuousIntegration.html"),
        ("GitHub Inc. (2024). ", "GitHub Actions Documentation: Automate your workflow from idea to production. ", "GitHub Docs. https://docs.github.com/en/actions"),
        ("Martin, R. C. (2018). ", "Clean Architecture: A Craftsman's Guide to Software Structure and Design. ", "Prentice Hall."),
        ("Universidad Tecnológica del Perú. (2026). ", "Lineamientos del Proyecto Final: Herramientas de Desarrollo (Ciclo 2026-II). ", "Facultad de Ingeniería de Sistemas e Informática."),
        ("Vercel Inc. (2024). ", "Deploying Next.js and Vite Applications on Vercel Edge Network. ", "Vercel Documentation. https://vercel.com/docs"),
        ("Vitest Dev. (2024). ", "Vitest: Next Generation Testing Framework. ", "Vitest Docs. https://vitest.dev/")
    ]
    for author_str, title_str, source_str in referencias_apa7:
        p_ref = doc.add_paragraph()
        p_ref.alignment = WD_ALIGN_PARAGRAPH.JUSTIFY
        p_ref.paragraph_format.space_before = Pt(0)
        p_ref.paragraph_format.space_after = Pt(2.5)
        p_ref.paragraph_format.left_indent = Inches(0.5)
        p_ref.paragraph_format.first_line_indent = Inches(-0.5)
        p_ref.paragraph_format.line_spacing = 1.15
        
        r_aut = p_ref.add_run(author_str)
        r_aut.font.name = 'Arial'
        r_aut.font.size = Pt(8.2)
        r_aut.font.color.rgb = COLOR_TEXT

        r_tit = p_ref.add_run(title_str)
        r_tit.font.name = 'Arial'
        r_tit.font.size = Pt(8.2)
        r_tit.font.italic = True
        r_tit.font.color.rgb = COLOR_TEXT

        r_src = p_ref.add_run(source_str)
        r_src.font.name = 'Arial'
        r_src.font.size = Pt(8.2)
        r_src.font.color.rgb = COLOR_TEXT

    # Guardar documento Word
    doc.save(OUTPUT_DOCX)
    print(f"3. Documento Word guardado exitosamente en: {OUTPUT_DOCX}")

def convert_to_pdf():
    """Convierte el documento DOCX a PDF usando LibreOffice headless."""
    print("4. Convirtiendo documento a PDF con LibreOffice...")
    out_dir = os.path.dirname(OUTPUT_PDF)
    cmd = [
        "libreoffice",
        "--headless",
        "--convert-to", "pdf",
        OUTPUT_DOCX,
        "--outdir", out_dir
    ]
    res = subprocess.run(cmd, capture_output=True, text=True)
    if res.returncode != 0:
        print("Error en conversión LibreOffice:", res.stderr)
        raise RuntimeError(res.stderr)
    print(f"5. PDF generado exitosamente en: {OUTPUT_PDF}")

    # Verificar con pdfinfo
    res_info = subprocess.run(["pdfinfo", OUTPUT_PDF], capture_output=True, text=True)
    print("\n=== METADATOS DEL PDF GENERADO ===")
    for line in res_info.stdout.splitlines():
        if "Pages:" in line or "Page size:" in line or "File size:" in line:
            print("  ", line)

if __name__ == "__main__":
    prepare_optimized_images()
    generate_apa7_document()
    convert_to_pdf()
